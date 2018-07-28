import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',  
  routes: [
    {
      path: '/',
      component: resolve => require(['../components/common/home.vue'], resolve),
      children:[
        { path: '/', redirect: 'note'},  
        {
          path: 'note',
          component: resolve => require(['../views/container/note.vue'], resolve),
          children: [
            { path: '/', redirect: 'detail'},  
            {
              path: 'detail',
              component: resolve => require(['../views/content/edit.vue'], resolve)
            }
          ]
        },
        {
          path: 'share',
          component: resolve => require(['../views/container/share.vue'], resolve),
          children: [
            { path: '/', redirect: 'doc'},  
            {
              path: 'doc/:code',
              component: resolve => require(['../views/share/index.vue'], resolve),
              props: true
            }
          ]
        }
      ]
    },
    {
      path: '/login',
      component: resolve => require(['../views/login/login.vue'], resolve)
    },
    {
      path: '/register',
      component: resolve => require(['../views/login/register.vue'], resolve)
    },
    { path: '/404', component: resolve => require(['../components/errorPage/404.vue'], resolve) },
    { path: '/401', component: resolve => require(['../components/errorPage/401.vue'], resolve) }
  ]
})
