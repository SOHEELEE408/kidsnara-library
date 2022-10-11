import {createWebHistory, createRouter} from "vue-router";

const routes = [
    {
        path: '/hello',
        name: 'Hello',
        component: () => import('@/components/HelloWorld'), // 동적 import
    },
    {
        path: '/',
        redirect: '/posts',
        name: 'TheContainer',
        component: () => import('@/components/layout/TheContainer'),
        children: [
            {
                path: '/posts',
                name: 'KidsnaraBooks',
                component: () => import('@/components/posts/KidsnaraBooks'),
            },
            {
                path: '/posts/detail',
                name: 'PostsDetail',
                component: () => import('@/components/posts/PostsDetail'),
            },
            {
                path: '/posts/reg',
                name: 'PostsReg',
                component: () => import('@/components/posts/PostsReg')
            },
            {
                path: '/barcode',
                name: 'BarcodeScanner',
                component: () => import('@/components/posts/BarcodeScanner'),
            }
        ]
    }
];

export const router = createRouter({
    history: createWebHistory(),
    routes,
});