<template>
  <div class="container">
    <div class="card" >
      <div class="card-body">
        <LibraryTable :list="list"
               :cnt="totalCnt"
               :getData="getPosts"
               :id="LibraryTable">
          <template v-slot:header>
            <th>Vol.</th>
            <th>도서명</th>
            <th>작가</th>
            <th>대여</th>
            <th class="text-center">비고</th>
          </template>
          <template v-slot:default="slotProps">
            <td>{{slotProps.row.id}}</td>
            <td>{{slotProps.row.title}}</td>
            <td>{{slotProps.row.author}}</td>
            <td>{{slotProps.row.createdDate}}</td>
            <td class="text-center">
              <router-link :to="{
                  path:'/posts/detail',
                  query:{id:slotProps.row.id}
                 }"
                           class="btn btn-sm btn-primary">
                🔎
              </router-link>
            </td>
          </template>
        </LibraryTable>
      </div>
    </div>

    <div class="row mt-3 float-right">
      <div class="col-auto">
        <router-link :to="{path:'/posts/reg'}"
                     class="btn btn-primary">
          <i class="fa fa-plus">등록</i>
        </router-link>
      </div>
    </div>

  </div>
</template>

<script>
import LibraryTable from "@/components/layout/BookTable";

export default {
  name: "Books",
  components:{LibraryTable},
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
    this.handleService()
  },
  methods:{
    handleService(){
      var params = new URLSearchParams()
      params.append("page",1)
      this.getPosts(params)
    },
    getPosts(params){
      this.axios.get("http://localhost:8081/books?"+params)  // 게시글: this.axios.get("http://127.0.0.1:8080/posts?" + params)
      .then(res=>{
        this.posts=res.data.content
        this.cnt=res.data.totalElements
      }).catch(e=>{
        alert(e)
      })
    },
  }
}
</script>

<style scoped>

</style>