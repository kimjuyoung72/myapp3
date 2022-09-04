package com.kh.myapp3.domain.svc;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kh.myapp3.domain.PEvent;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiPubImpl implements ApiPub {

    private static final String serviceKey = "0f741e87bbe646ce800c16ffbaf9b69d";

    @Override
    public List<PEvent> apiCall() {
        String xmlStr = "";
        List<PEvent> res = null;

        try {
            // 1. URL을 만들기 위한 StringBuilder
            StringBuilder urlBuilder = new StringBuilder("http://www.kopis.or.kr/openApi/restful/pblprfr?service=" + serviceKey ); /*요청url*/
            // 2. 오픈 API의요청 규격에 맞는 파라미터 생성
            urlBuilder.append("&" + URLEncoder.encode("stdate", "UTF-8") + "=" + URLEncoder.encode("20220801", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("eddate", "UTF-8") + "=" + URLEncoder.encode("20220830", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("cpage", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("rows", "UTF-8") + "=" + URLEncoder.encode("3", "UTF-8")); /*한 페이지 결과 수*/
//         urlBuilder.append("&" + URLEncoder.encode("shprfnm", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공연명*/
//         urlBuilder.append("&" + URLEncoder.encode("shprfnmfct", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공연시설명*/
//         urlBuilder.append("&" + URLEncoder.encode("prfplccd", "UTF-8") + "=" + URLEncoder.encode("7", "UTF-8")); /* 공연장코드 4 FC000003-01*/
//         urlBuilder.append("&" + URLEncoder.encode("signgucode", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*지역(시도)코드 2 11*/
//         urlBuilder.append("&" + URLEncoder.encode("signgucodesub", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*지역(구군)코드 4 1111*/
//         urlBuilder.append("&" + URLEncoder.encode("kidstate", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*아동공연여부 1 Y(지정안하면 디폴트는 ‘N’)*/
//         urlBuilder.append("&" + URLEncoder.encode("prfstate", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*공연상태코드 4 01*/
//         urlBuilder.append("&" + URLEncoder.encode("signgucodesub", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*지역(구군)코드 4 1111*/
//         urlBuilder.append("&" + URLEncoder.encode("shcate", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*장르코드 4 AAAA*/

            URL url = new URL(urlBuilder.toString());                           // 3. URL 객체 생성
            System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
            conn.setRequestMethod("GET");                                       // 5. 통신을 위한 메소드 SET.
//        conn.setRequestProperty("Content-type", "application/json");          // 6. 통신을 위한 Content-type SET.
//        conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("accept", "application/xml");
            System.out.println("Response code: " + conn.getResponseCode());     // 7. 통신 응답 코드 확인.
            BufferedReader rd;                                                  // 8. 전달받은 데이터를 BufferedReader 객체로 저장
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();     // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();                                 // 10. 객체 해제
            conn.disconnect();


//            System.out.println("sb.toString() : " + sb.toString());   // 11. 전달받은 데이터 확인.

            xmlStr = sb.toString();

            System.out.println(xmlStr);

            XmlMapper xmlMapper = new XmlMapper();  // 12. 받은 데이터를 필요한 폼으로 변환.

            //xml to java object
            res = xmlMapper.readValue(xmlStr, (new ArrayList<PEvent>()).getClass());
//            res = xmlMapper.readValue(xmlStr, (new ArrayList<Dbs>()).getClass());
            System.out.println(res);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;

    }
}
