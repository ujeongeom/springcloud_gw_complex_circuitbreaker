package com.kt.edu.thirdproject.common.util;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.kt.edu.thirdproject.common.logging.dto.LogDto;
import com.kt.edu.thirdproject.common.transform.model.CommonHeader;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.message.FormattedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;

import java.util.Date;

/**
 * Common Logging 마스킹 전용 Logging 클래스 일반 적인 로깅은 가능하나 마스킹이 지원 안함
 */

public class LogUtil {

    private static Logger log = LoggerFactory.getLogger(LogUtil.class);

    public static void trace(ServerWebExchange exchange, String format, Object... argument) {
        logging(exchange, Level.TRACE, format, argument);
    }

    public static void debug(ServerWebExchange exchange, String format, Object... argument) {
        logging(exchange, Level.DEBUG, format, argument);
    }

    public static void info(ServerWebExchange exchange, String format, Object... argument) {
        logging(exchange, Level.INFO, format, argument);
    }

    public static void warn(ServerWebExchange exchange, String format, Object... argument) {
        logging(exchange, Level.WARN, format, argument);
    }

    public static void error(ServerWebExchange exchange, String format, Object... argument) {
        logging(exchange, Level.ERROR, format, argument);
    }

    public static void error(ServerWebExchange exchange, String format, Throwable throwable) {
        logging(exchange, Level.ERROR, format, throwable);
    }

    /**
     * debug 마스킹 로그
     *
     * @param format
     * @param argument
     */
    public static void logging(ServerWebExchange exchange, Level level, String format, Object... argument) {

        String category = "DEBUG";
        String escapeMessage = "";
        if (format != null) {
            // format에 ctg가 있으면 공통 로그
            if (format.startsWith("[CTG:")) {
                category = format.substring(5, format.indexOf("]"));
                String saFormat = format.substring(format.indexOf("]") + 1);
                if (argument != null && argument.length > 0) {
                    escapeMessage = encodeMessage(getFormattedMessage(saFormat, argument));
                } else {
                    escapeMessage = encodeMessage(saFormat);
                }
            } else {
                if (argument != null && argument.length > 0) {
                    escapeMessage = encodeMessage(getFormattedMessage(format, argument));
                } else {
                    escapeMessage = encodeMessage(format);
                }
            }

        } else {
            if (argument != null && argument.length > 0) {
                escapeMessage = encodeMessage(getCompositeMessage(argument));
            } else {
                escapeMessage = "";
            }
        }

        CommonHeader commonHeader = null;
        String trace = null;
        if(exchange!=null) {
            commonHeader = exchange.getAttribute("commonHeader");
            if(commonHeader!=null) {
                trace = String.format("%s:1", commonHeader.getGlobalNo());
            }
            else {
                commonHeader = new CommonHeader();
                trace = "1:1";
            }
        }
        else {
            commonHeader = new CommonHeader();
            trace = "1:1";
        }

        LogDto logDto = new LogDto();

        logDto.setCategory(category);
        logDto.setDate(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
        logDto.setHeader(commonHeader);
        logDto.setLogLevel(level.toString());
        logDto.setMessage(escapeMessage);
        logDto.setService("edu-rest-apigw");
        logDto.setSource(getResourceInfo());
        logDto.setTrace(trace);
        logDto.setType("vue-gw");

        String logStr = JsonUtil.toJson(logDto);

        if (level == Level.TRACE) {
            log.trace("{}", logStr);
        } else if (level == Level.DEBUG) {
            log.debug("{}", logStr);
        } else if (level == Level.INFO) {
            log.info("{}", logStr);
        } else if (level == Level.WARN) {
            log.warn("{}", logStr);
        } else if (level == Level.ERROR) {
            log.error("{}", argument);
        }
    }

    private static String encodeMessage(String message) {
        JsonStringEncoder encoder = JsonStringEncoder.getInstance();
        char[] escapedJson = encoder.quoteAsString(message);
        String escapeMessage = String.valueOf(escapedJson);
        return escapeMessage;
    }

    private static String getFormattedMessage(String format, Object[] params) {
        FormattedMessage message = new FormattedMessage(format, params);
        return message.getFormattedMessage();
    }

    private static String getCompositeMessage(Object[] argument) {
        if (argument == null || argument.length == 0) {
            return "";
        }

        String message = "";
        if (argument.length > 0) {
            for (int i = 0; i < argument.length; i++) {
                if (argument[i] != null) {
                    message = message.concat(argument[i].toString());
                }
            }
        }
        return message;
    }

    public static void logging(ServerWebExchange exchange, Level level, String format, Throwable throwable) {

        String category = "DEBUG";
        String escapeMessage = "";
        if (format != null) {
            // format에 ctg가 있으면 공통 로그
            if (format.startsWith("[CTG:")) {
                category = format.substring(5, format.indexOf("]"));
                String saFormat = format.substring(format.indexOf("]") + 1);
                if (throwable != null) {
                    escapeMessage = encodeMessage(getFormattedMessage(saFormat, throwable.getStackTrace()));
                } else {
                    escapeMessage = encodeMessage(saFormat);
                }
            } else {
                if (throwable != null) {
                    escapeMessage = encodeMessage(getFormattedMessage(format, throwable.getStackTrace()));
                } else {
                    escapeMessage = encodeMessage(format);
                }
            }

        } else {
            if (throwable != null) {
                escapeMessage = encodeMessage(getCompositeMessage(throwable.getStackTrace()));
            } else {
                escapeMessage = "";
            }
        }

        CommonHeader commonHeader = exchange.getAttribute("commonHeader");

        LogDto logDto = new LogDto();

        logDto.setCategory(category);
        logDto.setDate(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
        logDto.setHeader(commonHeader);
        logDto.setLogLevel(level.toString());
        logDto.setMessage(escapeMessage);
        logDto.setService("icis-rest-apigw");
        logDto.setSource(getResourceInfo());
        logDto.setTrace("1:1");
        logDto.setType("NEXA-GW");

        String logStr = JsonUtil.toJson(logDto);

        log.error("{}", logStr);
    }

    private static String getResourceInfo() {

        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[3];
        String resourceInfo = "";
        String privClass = stackTraceElement.getClassName();
        privClass = privClass.substring(privClass.lastIndexOf(".") + 1);
        resourceInfo = String.format("%s.%s(%s)", privClass, stackTraceElement.getMethodName(),	stackTraceElement.getLineNumber());
        return resourceInfo;
    }

}