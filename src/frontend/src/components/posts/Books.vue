<template>
  <div class="container">
    <div class="card">
      <div class="card-body">
        <BookTable :list="list"
               :cnt="totalCnt"
               :getData="getPosts">
          <template v-slot:header>
            <th>vol.</th>
            <th>도서명</th>
            <th>작가명</th>
            <th>대여 가능 수</th>
            <th class="text-center">비고</th>
          </template>
          <template v-slot:default="slotProps">
            <td>{{slotProps.row.bookId}}</td>
            <td>{{slotProps.row.title}}</td>
            <td>{{slotProps.row.author}}</td>
            <td>{{slotProps.row.possibleCount}}</td>
            <td class="text-center">
              <router-link :to="{
                  name:'BookDetail',
                  params: {bookId: slotProps.row.bookId }
                 }"
                           class="btn btn-sm btn-primary">
                🔎
              </router-link>
            </td>
          </template>
        </BookTable>
      </div>
    </div>

  </div>
</template>

<script>
import BookTable from "@/components/layout/BookTable";
export default {
  name: "Books",
  components:{BookTable},
  data(){
    return{
      posts:[],
      cnt:0
    }
  },
  computed:{
    list(){
      return this.posts
    },
    totalCnt(){
      return this.cnt
    }
  },
  mounted(){
    this.loginCheck()
    this.handleService()
  },
  methods:{
    loginCheck(){
      if(localStorage.getItem('token') === null){
        console.log(localStorage.getItem('token'))
        this.$router.push("/login")
      }
    },
    handleService(){
      var params = new URLSearchParams()
      params.append("page",0)
      this.getPosts(params)
    },
    getPosts(params){
      this.axios.get("http://localhost:8081/books?"+params,
          {headers: { 'X-ACCESS-TOKEN': localStorage.getItem('token')}}
      )
          .then(res=>{
            this.posts=res.data.content
            console.log(res.data)
            this.cnt=res.data.totalElements
          }).catch(e=>{
            console.log(e)
      })
    },
  }
}
</script>

<style scoped>
</style>