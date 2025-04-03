import axios from 'axios'
import qs from 'qs'

const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
  paramsSerializer: {
    serialize: (params) => {
      return qs.stringify(params, { arrayFormat: 'repeat' })
    },
  },
})

http.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error(`API Error: ${error.message}`)
    return Promise.reject(error)
  },
)

export default http
