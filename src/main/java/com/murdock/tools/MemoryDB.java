package com.murdock.tools;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;

/**
 * @author weipeng2k 2017年10月17日 下午17:11:41
 */
public class MemoryDB {

    /**
     * 456MB
     * @param args
     */
    public static void main(String[] args) {
        DB db = DBMaker.memoryDB().make();
        ConcurrentMap<String, Integer> map = (ConcurrentMap<String, Integer>) db.hashMap("map").createOrOpen();

        // 14 char  + 2char = 16 char = 32byte 32MB
        for (int i = 0; i < 1_000_000; i++) {
            map.put(getRandomIp(), i);
        }

        System.out.println("done.");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 随机生成国内IP地址
     */
    public static String getRandomIp() {

        // ip范围
        int[][] range = {{607649792, 608174079},// 36.56.0.0-36.63.255.255
                {1038614528, 1039007743},// 61.232.0.0-61.237.255.255
                {1783627776, 1784676351},// 106.80.0.0-106.95.255.255
                {2035023872, 2035154943},// 121.76.0.0-121.77.255.255
                {2078801920, 2079064063},// 123.232.0.0-123.235.255.255
                {-1950089216, -1948778497},// 139.196.0.0-139.215.255.255
                {-1425539072, -1425014785},// 171.8.0.0-171.15.255.255
                {-1236271104, -1235419137},// 182.80.0.0-182.92.255.255
                {-770113536, -768606209},// 210.25.0.0-210.47.255.255
                {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    /*
     * 将十进制转换成ip地址
     */
    private static String num2ip(int ip) {
        int[] b = new int[4];
        String x = "";

        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(
                b[2]) + "." + Integer.toString(b[3]);

        return x;
    }
}
