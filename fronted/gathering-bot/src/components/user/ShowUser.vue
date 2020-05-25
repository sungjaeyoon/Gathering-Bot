<template>
  <div class="container">
    <button class="btn-primary btn my-5" href="add">사용자 추가</button>
    <button class="btn-primary btn my-5" v-on:click="getUser">사용자 받기</button>
    <table class="table">
      <thead class="thead-dark">
      <tr>
        <th scope="col">#</th>
        <th scope="col">이름</th>
        <th scope="col">이메일</th>
        <th scope="col">직책</th>
        <th scope="col">팀 이름</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(member, index) in this.memberList" v-bind:key="member.index">
        <th scope="row">{{ index+1 }}</th>
        <td>{{ member.name }}</td>
        <td>{{ member.email }}</td>
        <td>{{ member.position }}</td>
        <td>{{ member.teamName }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'ShowUser',
  data: function () {
    return {
      memberList: []
    }
  },
  created () {
    this.getUser()
  },
  methods: {
    // 유저 추가
    AddUser () {
      this.showModal = !this.showModal
      var user = {
        'name': '윤성재',
        'email': 'dbstjdwo1000@naver.com',
        'position': '사원',
        'teamName': '인증플랫폼'
      }
      this.memberList.push(user)
    },
    // 유저 리스트 받기
    getUser () {
      axios({
        method: 'get',
        url: 'http://localhost:8080/users',
        responseType: 'json'
      })
        .then(res => {
          console.log('get data', res.data)
          this.memberList = res.data
        })
    }
  }
}
</script>

<style scoped>

</style>
