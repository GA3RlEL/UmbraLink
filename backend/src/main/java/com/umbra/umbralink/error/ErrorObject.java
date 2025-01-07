package com.umbra.umbralink.error;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorObject {

  private int errorCode;
  private String message;
  private Date timestamp;

}
