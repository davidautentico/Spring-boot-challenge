package com.drosa.inditex.connect.api.rest.exceptions;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InditexConnectServiceErrorResponse {

  private final Map<String, String[]> parameters;

  private final String error;
}