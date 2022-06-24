import { createSlice } from '@reduxjs/toolkit';

const clickedTabSlice = createSlice({
  name: 'clickedTab',
  initialState: {
    clickedTab: null,
  },
  reducers: {
    changeTab(state, action) {
      state.clickedTab = action.payload;
    },
    deleteTab(state) {
      state.clickedTab = null;
    },
  },
});

export const { changeTab, deleteTab } = clickedTabSlice.actions;
export default clickedTabSlice.reducer;

// reducer: clickedTab.reducer
// action creators: clickedTab.actions.changeTab, clickedTab.actions.deleteTab
// actionType:
// - clickedTab.actions.changeTab.type: 'clickedTab/changeTab'
// - clickedTab.actions.deleteTab.type: 'clickedTab/deleteTab'
