<template>
  <div class="login-container" id="login-content">
    <form @submit.prevent="login">
      <h2 class="mb-3">로그인</h2>
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
      <div class="alternative-option mt-4">
        계정이 없으신가요? <span @click="moveToRegister">Register(가입하기)</span>
      </div>
      <button type="submit" class="mt-4 btn-pers" id="login_button">
        로그인
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
  name: "Login",
  data() {
    return {
      email: "",
      password: "",
      token: ""
    };
  },
  methods: {
    login(submitEvent) {

      let params = {
        "email": this.email,
        "password": this.password
      }
      this.axios.post('http://localhost:8081/users/login',
          JSON.stringify(params),
          {headers:{'Content-type':'application/json'}}
      )
      .then(res => {
        this.token = res.data.token
        localStorage.setItem('token', this.token)
        alert(localStorage.getItem('token'))
        this.$router.push("/books");
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
    },

    moveToRegister() {
      this.$router.push("/join");
    },
  },
};
</script>
<style>
#login-content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: 1px solid lightgray;
  padding: 4rem 4rem;
  border-radius: 5px;
  background: #fefefe;
}

.login-container {
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