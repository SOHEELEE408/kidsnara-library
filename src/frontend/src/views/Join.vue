<template>
  <div class="join-container" id="join-content">
    <form @submit.prevent="register">
      <h2 class="mb-3">회원가입</h2>
      <div class="input">
        <label for="username">이름</label>
        <input
            class="form-control"
            id="username"
            type="text"
            name="username"
            v-model="username"
            placeholder="이름을 입력해주세요."
        />
      </div>
      <div class="input">
        <label for="email">Email(이메일)</label>
        <input
            class="form-control"
            id="email"
            type="text"
            name="email"
            v-model="email"
            placeholder="email@adress.com"
        />
      </div>
      <div class="input">
        <label for="password">Password(비밀번호)</label>
        <input
            class="form-control"
            id="password"
            type="password"
            name="password"
            v-model="password"
            placeholder="password123"
        />
      </div>
      <div class="input">
        <label>권한</label>
        <label for="user">일반 회원</label>
        <input
            id="user"
            type="radio"
            value="USER"
            v-model= "role"
        />
        <label for="admin">관리자</label>
        <input
            id="admin"
            type="radio"
            value="ADMIN"
            v-model= "role"
        />
      </div>
      <div class="alternative-option mt-4">
        이미 회원이신가요? <span @click="moveToLogin">로그인</span>
      </div>
      <button type="submit" class="mt-4 btn-pers" id="register_button">
        회원가입
      </button>
      <div
          class="alert alert-warning alert-dismissible fade show mt-5 d-none"
          role="alert"
          id="alert_1"
      >
        Lorem ipsum dolor sit amet consectetur, adipisicing elit.
        <button
            type="button"
            class="btn-close"
            data-bs-dismiss="alert"
            aria-label="Close"
        ></button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Join",
  data(){
    return {
      username: "",
      email: "",
      password: "",
      role: ""
    }
  },
  methods: {
    register(submitEvent) {
      // data update
      let params = {
        "username": this.username,
        "email": this.email,
        "password": this.password,
        "role": this.role
      }
      this.axios.post(
          'http://localhost:8081/users/join',
          JSON.stringify(params),
          {headers : {'Content-Type': 'application/json'}}
      )
      .then(res => {
        localStorage.setItem('userId', res.data.userId)
        this.$router.push("/login");
      })
      .catch(error => {
        alert(error.response.data.message);
        console.log(error)
      });
    },
    moveToLogin() {
      this.$router.push("/login");
    },
  }
}
</script>

<style>
#join-content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: 1px solid lightgray;
  padding: 4rem 4rem;
  border-radius: 5px;
  background: #fefefe;
}

.join-container {
  width: 400px;
  max-width: 95%;
}
.input {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}
.input > label {
  text-align: start;
}
.input > input {
  margin-top: 6px;
  height: 38px !important;
}
/* From uiverse.io */
.btn-pers {
  position: relative;
  left: 50%;
  padding: 1em 2.5em;
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 2.5px;
  font-weight: 700;
  color: #000;
  background-color: #fff;
  border: none;
  border-radius: 45px;
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
  transform: translateX(-50%);
}
.btn-pers:hover {
  background-color: #198754;
  box-shadow: 0px 15px 20px rgba(46, 229, 157, 0.4);
  color: #fff;
  transform: translate(-50%, -7px);
}
.btn-pers:active {
  transform: translate(-50%, -1px);
}
/*  */
.alternative-option {
  text-align: center;
}
.alternative-option > span {
  color: #0d6efd;
  cursor: pointer;
}
</style>