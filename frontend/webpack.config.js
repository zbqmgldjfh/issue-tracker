require('dotenv').config();

const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');

const isProduction = process.env.NODE_ENV === 'production';
const PORT = process.env.PORT || 3000;

module.exports = {
  mode: isProduction ? 'production' : 'development',
  devtool: isProduction ? 'hidden-source-map' : 'source-map',
  entry: './src/index.tsx',
  output: {
    filename: '[name].js',
    path: path.join(__dirname, '/dist'),
  },
  resolve: {
    modules: [path.resolve(__dirname, './'), 'node_modules'],
    extensions: ['.js', '.jsx', '.ts', '.tsx'],
  },
  module: {
    rules: [
      {
        test: /\.(ts|tsx)$/,
        use: {
          loader: 'ts-loader',
          options: {
            transpileOnly: !isProduction,
          },
        },
        // eslint-disable-next-line array-element-newline
      },
      {
        test: /\.(png|jpe?g|gif)$/i,
        use: [
          {
            loader: 'file-loader',
          },
        ],
        // eslint-disable-next-line array-element-newline
      },
      {
        test: /\.svg$/i,
        issuer: /\.[jt]sx?$/,
        use: ['@svgr/webpack'],
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, 'public', 'index.html'),
      hash: true,
    }),
  ],
  devServer: {
    host: 'localhost',
    port: PORT,
    open: true,
    hot: true,
    compress: true,
    historyApiFallback: true,
  },
  performance: {
    hints: false,
    maxEntrypointSize: 512000,
    maxAssetSize: 512000,
  },
};
