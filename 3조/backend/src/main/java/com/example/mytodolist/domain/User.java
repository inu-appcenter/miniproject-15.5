package com.example.mytodolist.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_tb")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false,name = "user_uid")
    private String uid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, name = "user_password")
    private String password;

    @Column(nullable = false,name = "user_name")
    private String name;

    @Column(nullable = false, name = "user_email")
    private String email;

    //생성자로 초기화 하지 않으므로 기본값으로 0이 설정이 된다.
    //이후에 할일 성공 개수 기준으로 레벨을 증가시킴.
    @Column(nullable = false, name = "user_level")
    private int level;

    //생성자로 초기화 하지 않아도, @Builder.Default 를 이용하여 빈 ArrayList 로 초기화 함.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Todo> todoList = new ArrayList<>();


    //이 아래부터 스프링 시큐리티를 위한 추가
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "user_roles")
    private List<String> roles = new ArrayList<>();

    //계정이 가지고 있는 권한 목록을 리턴합니다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    //계정의 이름을 리턴, 일반적으로 아이디를 리턴
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername(){
        return this.uid;
    }

    //계정이 만료됐는지 리턴, true는 만료되지 않았다는 의미
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    //계정이 잠겨있는지 리턴, true는 잠기지 않았다는 의미
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    //비밀번호가 만료됐는지 리턴, true는 만료되지 않았다는 의미
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    // 계정이 활성화돼 있는지 리턴, true는 활성화 상태를 의미
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled(){
        return true;
    }

    @Builder
    public User(String uid,String password,List roles,String name, String email)
    {
        this.uid = uid;
        this.password = password;
        this.roles = roles;
        this.name = name;
        this.email = email;
        this.level = 0;
    }

    public void updateUserInfo(String name, String email){
        this.name = name;
        this.email = email;
    }

    //레벨 업 메소드
    public void LevelUp()
    {
        this.level = this.level + 1;
    }

    //레벨 다운 메소드
    public void LevelDown(){this.level = this.level - 1;}

}
