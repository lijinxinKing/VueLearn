let proxyObj = {}

proxyObj['/'] = {
    //websocket
    ws: false,
    //目标地址
    target: 'http://127.0.0.1:8081',
    //发送请求头host会被设置target
    changeOrigin: true,
    //不重写请求地址
    pathReWrite: {
        '^/': '/'
    }
};


proxyObj['/ws'] = {
    ws: true,
    target: 'ws://10.119.169.121:8081'
};


module.exports = {
    devServer: {
        host: '10.119.169.121',
        port: 8080,
        proxy: proxyObj
    }
}