const HtmlWebpackPlugin = require('html-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const path = require('path');
const webpack = require('webpack');

module.exports = {
  entry: {
    main: path.join(__dirname, '../src/index.tsx'),
  },
  output: {
    path: path.join(__dirname, '../dist'),
    publicPath: '/',
    filename: '[name].[contenthash].js',
    chunkFilename: '[name].[contenthash].chunk.js',
    clean: true,
  },
  resolve: {
    extensions: ['.js', '.jsx', '.ts', '.tsx'],
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/, // tsx나 ts파일을 발견하면
        use: ['babel-loader', 'ts-loader'], // 해당 loader를 통해서 버전을 변환
      },
      {
        test: /\.jsx?$/,
        use: 'babel-loader',
        exclude: /node_modules/,
      },
      {
        test: /\.css$/i,
        use: ['style-loader', 'css-loader'],
      },
      {
        test: /\.(png|jpe?g|gif)$/,
        use: ['file-loader'],
      },
      {
        test: /\.svg$/,
        use: ['@svgr/webpack'],
      },
      {
        test: /\.c?js$/,
        enforce: "pre",
        use: ["source-map-loader"],
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './public/index.html',
    }),
    new CleanWebpackPlugin(),
    new webpack.DefinePlugin({
      'process.env.CLIENT_URL': JSON.stringify(
          process.env.NODE_ENV === 'production' ? 'https://bootme.co.kr/' :
              process.env.NODE_ENV === 'staging' ? 'https://staging.bootme.co.kr/' :
                  process.env.NODE_ENV === 'dev' ? 'http://localhost:3000/' :
                      'http://localhost:3000/'
      ),
      'process.env.SERVER_URL': JSON.stringify(
          process.env.NODE_ENV === 'production' ? 'https://api.bootme.co.kr/' :
              process.env.NODE_ENV === 'staging' ? 'https://staging.api.bootme.co.kr/' :
                  process.env.NODE_ENV === 'dev' ? 'http://localhost:8000/' :
                      'http://localhost:3000/'
      ),
    }),
  ],
};
