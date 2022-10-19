import {createWebHistory, createRouter} from "vue-router";

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login'), // 동적 import
    },
    {
        path: '/join',
        name: 'Join',
        component: () => import('@/views/Join'), // 동적 import
    },
    {
        path: '/',
        redirect: '/books',
        name: 'TheContainer',
        component: () => import('@/views/TheContainer'),
        children: [
            {
                path: '/books',
                name: 'Books', // 만들어지는 js 파일명
                component: () => import('@/components/posts/Books'),
            },
            {
                path: '/books/detail',
                name: 'BookDetail',
                component: () => import('@/components/posts/BookDetail'),
            },
            {
                path: '/books/regist',
                name: 'BookReg',
                component: () => import(
                    /* webpackChunkName: "bookregist" */ '@/components/posts/BookReg')
            },
            {
                path: '/books/regist/barcode',
                name: 'BarcodeScanner',
                component: () => import(
                    /* webpackChunkName: "bookregist" */ '@/components/posts/BarcodeScanner'),
            }
        ]
    }
];

export const router = createRouter({
    history: createWebHistory(),
    routes,
});