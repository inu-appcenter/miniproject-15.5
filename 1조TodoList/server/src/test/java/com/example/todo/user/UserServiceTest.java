package com.example.todo.user;

//@SpringBootTest
//@ActiveProfiles("test")
class UserServiceTest {
/*
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    @Test
    void getUser() throws Exception {
        User mockUser =  new User("namu1234@gmail.com", "test1234", "namu");
        userRepository.save(mockUser);
        Long userId = mockUser.getUserId();

        given(userRepository.findById(userId)).willReturn(Optional.of(mockUser));

        UserResponseDto unvalidUserResponseDto = userService.getUser(userId);

        assertThat(unvalidUserResponseDto).isNotNull();
        assertThat(unvalidUserResponseDto.getUserId()).isEqualTo(userId);
        assertThat(unvalidUserResponseDto.getLoginId()).isEqualTo("namu1234@gmail.com");
        assertThat(unvalidUserResponseDto.getPassword()).isEqualTo("test1234");
        assertThat(unvalidUserResponseDto.getName()).isEqualTo("namu");
    }
*/
}