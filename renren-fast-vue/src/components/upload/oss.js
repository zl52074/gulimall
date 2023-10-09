import http from '@/utils/httpRequest.js'

export function policy(){
  return  new Promise((resolve,reject)=>{
    http({
      url: http.adornUrl("/thirdparty/oss/policy"),
      method: "get",
      params: http.adornParams({})
    }).then(({ data }) => {
      resolve(data);
    })
  });
}

export function remove(file){
  return  new Promise((resolve,reject)=>{
    http({
      url: http.adornUrl("/thirdparty/oss/delete"),
      method: "post",
      data: http.adornData([file],false)
    }).then(({ data }) => {
      resolve(data);
    })
  });
}
