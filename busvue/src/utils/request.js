import axios from 'axios'

const request = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000
})

// 响应拦截器
request.interceptors.response.use(
    response => {
        return response.data
    },
    error => {
        ElMessage.error(error.response?.data?.message || '服务器错误')
        return Promise.reject(error)
    }
)

export default request 