module.exports={
    outputDir: 'admin',
    assetsDir:'static',
    publicPath:'./',
    devServer:{
        port:8080,
        proxy: {
            '/api': {
                // target: 'http://183.62.138.17:8760',
                // target: 'http://120.25.232.55:8760',
                target: 'http://106.53.145.95:8760',
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/api': 'api'
                }
            }
        }
    },
    pages: {
        index: {
            // add here ---start---
            entry: ['node_modules/babel-polyfill/dist/polyfill.js', 'src/main.js'],
            // add here ---end---
            template: 'public/index.html',
            filename: 'index.html',
            chunks: ['chunk-vendors', 'chunk-common', 'index'],
        }
    }
}