package com.kt.edu.thirdproject.common.security;

import java.util.Set;

record ProfileResponse(String username, Set<String> roles) {
}