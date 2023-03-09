package com.adobe.prj.dto;

import java.util.Date;

public record Report(String email, Date orderDate, double total) {}