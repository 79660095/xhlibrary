package com.wxh.sdk.des3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import it.sauronsoftware.base64.Base64;

/**
014
 * <p>
015
 * BASE64������빤�߰�
016
 * </p>
017
 * <p>
018
 * ����javabase64-1.3.1.jar
019
 * </p>
020
 *
021
 * @author IceWee
022
 * @date 2012-5-19
023
 * @version 1.0
024
 */

public class Base64Utils {
	/**
	028
	     * �ļ���ȡ��������С
	029
	     */
	
	  private static final int CACHE_SIZE = 1024;

	     

	    /**
	033
	     * <p>
	034
	     * BASE64�ַ�������Ϊ����������
	035
	     * </p>
	036
	     *
	037
	     * @param base64
	038
	     * @return
	039
	     * @throws Exception
	040
	     */
	
	    public static byte[] decode(String base64) throws Exception {
	
	        return Base64.decode(base64.getBytes());
	
	    }
	    /**
	    046
	         * <p>
	    047
	         * ���������ݱ���ΪBASE64�ַ���
	    048
	         * </p>
	    049
	         *
	    050
	         * @param bytes
	    051
	         * @return
	    052
	         * @throws Exception
	    053
	         */
	    
	        public static String encode(byte[] bytes) throws Exception {
	    
	            return new String(Base64.encode(bytes));
	    
	        }
	    
	         
	    
	        /**
	    059
	         * <p>
	    060
	         * ���ļ�����ΪBASE64�ַ���
	    061
	         * </p>
	    062
	         * <p>
	    063
	         * ���ļ����ã����ܻᵼ���ڴ����
	    064
	         * </p>
	    065
	         *
	    066
	         * @param filePath �ļ�����·��
	    067
	         * @return
	    068
	         * @throws Exception
	    069
	         */
	    
	        public static String encodeFile(String filePath) throws Exception {
	    
	            byte[] bytes = fileToByte(filePath);
	    
	            return encode(bytes);
	    
	        }
	    
	         
	    
	        /**
	    076
	         * <p>
	    077
	         * BASE64�ַ���ת���ļ�
	    078
	         * </p>
	    079
	         *
	    080
	         * @param filePath �ļ�����·��
	    081
	         * @param base64 �����ַ���
	    082
	         * @throws Exception
	    083
	         */
	    
	        public static void decodeToFile(String filePath, String base64) throws Exception {
	    
	            byte[] bytes = decode(base64);
	    
	            byteArrayToFile(bytes, filePath);
	    
	        }
	    
	         
	   
	        /**
	    090
	         * <p>
	    091
	         * �ļ�ת��Ϊ����������
	    092
	         * </p>
	    093
	         *
	    094
	         * @param filePath �ļ�·��
	    095
	         * @return
	    096
	         * @throws Exception
	    097
	         */
	    
	        public static byte[] fileToByte(String filePath) throws Exception {
	    
	            byte[] data = new byte[0];
	    
	            File file = new File(filePath);
	   
	            if (file.exists()) {
	    
	                FileInputStream in = new FileInputStream(file);
	    
	                ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
	    
	                byte[] cache = new byte[CACHE_SIZE];
	    
	                int nRead = 0;
	    
	                while ((nRead = in.read(cache)) != -1) {
	    
	                    out.write(cache, 0, nRead);
	    
	                    out.flush();
	    
	               }
	    
	                out.close();
	    
	                in.close();
	    
	                data = out.toByteArray();
	    
	             }
	   
	            return data;
	    
	        }
	    
	         
	    
	        /**
	    118
	         * <p>
	    119
	         * ����������д�ļ�
	    120
	         * </p>
	    121
	         *
	    122
	         * @param bytes ����������
	    123
	         * @param filePath �ļ�����Ŀ¼
	    124
	         */
	    
	        public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
	    
	            InputStream in = new ByteArrayInputStream(bytes);  
	    
	            File destFile = new File(filePath);
	    
	            if (!destFile.getParentFile().exists()) {
	    
	                destFile.getParentFile().mkdirs();
	    
	            }
	    
	            destFile.createNewFile();
	    
	            OutputStream out = new FileOutputStream(destFile);
	    
	            byte[] cache = new byte[CACHE_SIZE];
	    
	            int nRead = 0;
	    
	            while ((nRead = in.read(cache)) != -1) {  
	    
	                out.write(cache, 0, nRead);
	    
	                out.flush();
	    
	            }
	    
	            out.close();
	    
	            in.close();
	    
	        }

}
