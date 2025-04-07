import axios from 'axios'
import qs from 'qs'
import appEnv from 'app-env'

const http = axios.create({
  baseURL: appEnv.VITE_API_BASE_URL,
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
