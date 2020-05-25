import Vue from 'vue'
import Router from 'vue-router'
import MainPage from '../components/MainPage'
import AddTable from '../components/AddTable'
import ShowUser from '../components/user/ShowUser'
import AddUserForm from '../components/user/AddUserForm'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'MainPage',
      component: MainPage
    },
    {
      path: '/result',
      name: 'MainPage',
      component: MainPage
    },
    {
      path: '/user',
      name: 'ShowUser',
      component: ShowUser,
      children: [
        {
          path: '/add',
          component: AddUserForm
        }
      ]
    },
    {
      path: '/create',
      name: 'MainPage',
      component: AddTable,
      children: [
        {
          path: '/table',
          component: MainPage
        }
      ]
    }
  ]
})
