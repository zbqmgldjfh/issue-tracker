import { configureStore } from '@reduxjs/toolkit';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import App from './App';
import clickedTabReducer from './modules/clickedTab';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
const store = configureStore({
  reducer: {
    clickedTab: clickedTabReducer,
  },
});
export type RootState = ReturnType<typeof store.getState>;

root.render(
  <Provider store={store}>
    <App />,
  </Provider>,
);
