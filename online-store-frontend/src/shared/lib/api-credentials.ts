import Axios from 'axios';

import {env} from '@/config/env';

export const apiWithCredentials = Axios.create({
  baseURL: env.API_URL,
  withCredentials: true,
});
apiWithCredentials.interceptors.response.use((response) => response.data);
//dev interceptor for mock data
/*apiWithCredentials.interceptors.request.use(config=> {
  if (config.url && !config.url.endsWith('.json')){
    config.url += '.json'
  }
  return config;
})*/
