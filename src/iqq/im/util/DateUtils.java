 /*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 /**
 * Project  : WebQQCoreAsync
 * Package  : iqq.im.util
 * File     : DateUtils.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-5
 * License  : Apache License 2.0 
 */
package iqq.im.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class DateUtils {
   public static Date parse(JSONObject jsonobj) throws ParseException, JSONException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d = format.parse(jsonobj.getInt("year") + "-" + jsonobj.getInt("month")+ "-" + jsonobj.getInt("day"));
        return d;
    }
   
   public static long nowTimestamp(){
	   return System.currentTimeMillis()/1000;
   }
}
