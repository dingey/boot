package com.d.util;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * json反序列化
 *
 * @auther d
 */
public class StringArrayDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < treeNode.size(); i++) {
            String node = treeNode.path(i).toString();
            int start = 0, end = 0;
            if (node.charAt(0) == '"') {
                start = 1;
            }
            if (node.charAt(node.length() - 1) == '"') {
                end = node.length() - 1;
            }
            sb.append(node.substring(start, end));
            if (i < treeNode.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String json = "{\"id\":1,\"name\":[\"abc\",\"efg\"]}";
        StringArraySerializer.Man man = JsonUtil.build().fromJson(json, StringArraySerializer.Man.class);
        System.out.println(man.getName());
    }
}
