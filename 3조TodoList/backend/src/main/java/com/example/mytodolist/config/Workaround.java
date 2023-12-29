package com.example.mytodolist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;


import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


//OpenAPI(Swagger) 문서를 변환하는 필터 역할을 하는 클래스
@Component
public class Workaround implements WebMvcOpenApiTransformationFilter {

    @Override //부모 클래스나 인터페이스의 메서드를 재정의 한다는 뜻, 여기서는 transform 메서드를 재정의 하였다.
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        OpenAPI openApi = context.getSpecification(); //OpenApi객체를 매개변수로 받아온 현재 OpenApi 문서로 초기화
        Server localServer = new Server(); //Server 객체 생성
        localServer.setDescription("local"); //설명을 local로 설정
        localServer.setUrl("http://localhost:8080/"); //url을 해당 값으로 설정

        Server testServer = new Server();//Server 객체 생성
        testServer.setDescription("test");//설명을 test로 설정
        testServer.setUrl("http://hyeongjun.na2ru2.me/");//url을 해당 값으로 설정
        openApi.setServers(Arrays.asList(localServer, testServer)); //OpenAPI 문서의 서버 목록을 localServer와 , testServer로 설정
        return openApi;
    }


    @Override //부모 클래스나 인터페이스의 메서드를 재정의 한다는 뜻, 여기서는 support 메서드를 재정의 하였다.
    public boolean supports(DocumentationType documentationType) {
        //우리가 사용하는 OpenAPI 문서가 Swagger 3.0 이므로, Swagger3.0 일때만 이 클래스가 실행된다.
        return documentationType.equals(DocumentationType.OAS_30);
    }
}