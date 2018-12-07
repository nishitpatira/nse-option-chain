package com.nishitp.otpiondata.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pair<L,R> {
	
	L url;
	R symbol;

}
