package com.ge.pmms.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSingleton {

	private static Gson gson;

	private GsonSingleton() {
	};

	public static Gson getGsonInstance() {
		if (gson == null) {
//			gson = new Gson();
			//use this to fix null object returns nothing 
			gson = new GsonBuilder().serializeNulls().create();
		}
		return gson;
	}
}
