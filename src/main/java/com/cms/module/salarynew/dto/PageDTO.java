package com.cms.module.salarynew.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PageDTO<T> {
  private List<T> items;
  private long total;
}