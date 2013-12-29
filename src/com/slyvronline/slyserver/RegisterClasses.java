package com.slyvronline.slyserver;

import com.esotericsoftware.kryo.Kryo;

public class RegisterClasses {

	public static void register(Kryo kryo){
	    kryo.register(SomeRequest.class);
	    kryo.register(SomeResponse.class);
	}

}
