package com.android.ysq.utils;


import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import android.text.TextUtils;

public class FastJsonImpl implements IJson {

	/**
	 * 将对象转换json字符串
	 *
	 * @param obj 对象
	 * @return json字符串
	 */
	public String toJSONString(Object obj) {
		String json = "";
		if (obj instanceof ArrayList) {
			json = JSONArray.toJSONString(obj);
		} else {
			json = JSON.toJSONString(obj);
		}
		return json;
	}

	/**
	 * 将JSON串转换成List,JSON串格式如：[{"id":1,"name":"LiLei"}]，不能缺少'[ ]',否则会报异常
	 * 
	 * @param text
	 *            JSON串
	 * @param clazz
	 *            目标Class
	 * @return 如果text为null或者""或者clazz为null，则返回null，否则返回List。
	 */
	public <T> List<T> parseArray(String text, Class<T> clazz) {
		if (TextUtils.isEmpty(text) || null == clazz) {
			return null;
		}
		return JSON.parseArray(text, clazz);
	}

	/**
	 * 将JSON串中的部分数据转换成List,JSON串格式如：
	 * {"id":1,"name":"LiLei","scores":[{"name":"语文","result":85}]}，
	 * 不能最外围不能用'[ ]'包含,否则会报异常
	 * 
	 * @param text
	 *            JSON串
	 * @param specialKey
	 *            指定的key名称
	 * @param clazz
	 *            目标Class
	 * @return 如果出现一下情况则返回null：1.text为null或者""; 2.@specialKey为null或者"";
	 *         3.clazz为null.
	 * 
	 */
	public <T> List<T> parseSpecialArray(String text, String specialKey, Class<T> clazz) {
		if (TextUtils.isEmpty(text) || TextUtils.isEmpty(specialKey) || null == clazz) {
			return null;
		}
		JSONArray result = JSON.parseObject(text).getJSONArray(specialKey);
		if (null == result) {
			return null;
		}
		return JSON.parseArray(result.toJSONString(), clazz);
	}


	public <T> T parseObject(String text, Class<T> clazz) {
		if (TextUtils.isEmpty(text) || null == clazz) {
			return null;
		}
		return JSON.parseObject(text, clazz);
	}

	public <T> T parseSpecialObject(String text, String specialKey, Class<T> clazz) {
		if (TextUtils.isEmpty(text) || TextUtils.isEmpty(specialKey) || null == clazz) {
			return null;
		}
		JSONObject result = JSON.parseObject(text).getJSONObject(specialKey);
		if (null == result) {
			return null;
		}
		return JSON.parseObject(result.toJSONString(), clazz);
	}

	public List<?> toList(String json) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		return JSONObject.parseArray(json, List.class);
	}

}
