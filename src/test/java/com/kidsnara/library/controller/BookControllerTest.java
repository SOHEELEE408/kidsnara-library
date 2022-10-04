package com.kidsnara.library.controller;

import com.google.gson.Gson;
import com.kidsnara.library.config.exceptionhandler.BaseErrorResult;
import com.kidsnara.library.config.exceptionhandler.BaseException;
import com.kidsnara.library.config.exceptionhandler.GlobalExceptionHandler;
import com.kidsnara.library.domain.library.Book;
import com.kidsnara.library.dto.book.BookRegisterReq;
import com.kidsnara.library.dto.book.BookRes;
import com.kidsnara.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static com.kidsnara.library.constant.BookConstants.ACCESS_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    void init(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void 책등록실패_토큰이헤더에없음() throws Exception {
        // given
        final String url = "/books";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(bookRequest("9999", "책 이름", "작가명", "출판사", 10000, 1, "문학")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 책등록실패_isbn이null() throws Exception {
        // given
        final String url = "/books";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(ACCESS_TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVU0VSX0VNQUlMIjoidGVzdEBlbWFpbC5jb20iLCJpc3MiOiJraWRzbmFyYSIsIlVTRVJfUk9MRSI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NDg3NjY0NX0.xuKp64S2Yk02NhFtq_YeBS2cp9Lmf_BlhIQT-SjOBcI")
                        .content(gson.toJson(bookRequest(null, "책 이름", "작가명", "출판사", 10000, 1, "문학")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 책등록실패_title이null() throws Exception {
        // given
        final String url = "/books";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(ACCESS_TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVU0VSX0VNQUlMIjoidGVzdEBlbWFpbC5jb20iLCJpc3MiOiJraWRzbmFyYSIsIlVTRVJfUk9MRSI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NDg3NjY0NX0.xuKp64S2Yk02NhFtq_YeBS2cp9Lmf_BlhIQT-SjOBcI")
                        .content(gson.toJson(bookRequest("9999", null, "작가명", "출판사", 10000, 1, "문학")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 책등록실패_작가명이null() throws Exception {
        // given
        final String url = "/books";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(ACCESS_TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVU0VSX0VNQUlMIjoidGVzdEBlbWFpbC5jb20iLCJpc3MiOiJraWRzbmFyYSIsIlVTRVJfUk9MRSI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NDg3NjY0NX0.xuKp64S2Yk02NhFtq_YeBS2cp9Lmf_BlhIQT-SjOBcI")
                        .content(gson.toJson(bookRequest("9999", "책 이름", null, "출판사", 10000, 1, "문학")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 책등록실패_출판사명이null() throws Exception {
        // given
        final String url = "/books";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(ACCESS_TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVU0VSX0VNQUlMIjoidGVzdEBlbWFpbC5jb20iLCJpc3MiOiJraWRzbmFyYSIsIlVTRVJfUk9MRSI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NDg3NjY0NX0.xuKp64S2Yk02NhFtq_YeBS2cp9Lmf_BlhIQT-SjOBcI")
                        .content(gson.toJson(bookRequest("9999", "책 이름", "작가명", null, 10000, 1, "문학")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 책등록실패_장르가null() throws Exception {
        // given
        final String url = "/books";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(ACCESS_TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVU0VSX0VNQUlMIjoidGVzdEBlbWFpbC5jb20iLCJpc3MiOiJraWRzbmFyYSIsIlVTRVJfUk9MRSI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NDg3NjY0NX0.xuKp64S2Yk02NhFtq_YeBS2cp9Lmf_BlhIQT-SjOBcI")
                        .content(gson.toJson(bookRequest("9999", "책 이름", "작가명", "출판사", 10000, 1, null)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 책등록실패_bookService에서에러Throw() throws Exception {
        // given
        final String url = "/books";
        doThrow(new BaseException(BaseErrorResult.DUPLICATED_BOOK_REGISTER))
                .when(bookService)
                .saveBook(any(Book.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(ACCESS_TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVU0VSX0VNQUlMIjoidGVzdEBlbWFpbC5jb20iLCJpc3MiOiJraWRzbmFyYSIsIlVTRVJfUk9MRSI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NDg3NjY0NX0.xuKp64S2Yk02NhFtq_YeBS2cp9Lmf_BlhIQT-SjOBcI")
                        .content(gson.toJson(bookRequest("9999", "책 이름", "작가명", "출판사", 10000, 1, "문학")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("invalidBookAddParameter")
    public void 책등록실패_잘못된파라미터(String isbn, String title, String author, String publisher, int price, int count, String genre) throws Exception {

        // given
        final String url = "/books";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(ACCESS_TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVU0VSX0VNQUlMIjoidGVzdEBlbWFpbC5jb20iLCJpc3MiOiJraWRzbmFyYSIsIlVTRVJfUk9MRSI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NDg3NjY0NX0.xuKp64S2Yk02NhFtq_YeBS2cp9Lmf_BlhIQT-SjOBcI")
                        .content(gson.toJson(bookRequest(isbn, title, author, publisher, price, count, genre)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 책등록성공() throws Exception {
        // given
        final String url = "/books";
        final BookRes bookRes = BookRes.builder()
                .bookId(-1L)
                .title("책 이름")
                .build();
        doReturn(bookRes).when(bookService).saveBook(any(Book.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(ACCESS_TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVU0VSX0VNQUlMIjoidGVzdEBlbWFpbC5jb20iLCJpc3MiOiJraWRzbmFyYSIsIlVTRVJfUk9MRSI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NDg3NjY0NX0.xuKp64S2Yk02NhFtq_YeBS2cp9Lmf_BlhIQT-SjOBcI")
                        .content(gson.toJson(bookRequest("9999", "책 이름", "작가명", "출판사", 10000, 1, "문학")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isCreated());

        BookRes response = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), BookRes.class);

        assertThat(response.getTitle()).isEqualTo("책 이름");
        assertThat(response.getBookId()).isNotZero();

    }

    private BookRegisterReq bookRequest(String isbn, String title, String author, String publisher, int price, int count, String genre){
        return BookRegisterReq.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .publisher(publisher)
                .price(price)
                .count(count)
                .genre(genre)
                .build();
    }

    private static Stream<Arguments> invalidBookAddParameter(){
        return Stream.of(
                Arguments.of(null, "책 이름", "작가명", "출판사", 10000, 1, "문학"),
                Arguments.of("9999", null, "작가명", "출판사", 10000, 1, "문학"),
                Arguments.of("9999", "책 이름", null, "출판사", 10000, 1, "문학"),
                Arguments.of("9999", "책 이름", "작가명", null, 10000, 1, "문학"),
                Arguments.of("9999", "책 이름", "작가명", "출판사", 10000, 1, null)
        );
    }
}