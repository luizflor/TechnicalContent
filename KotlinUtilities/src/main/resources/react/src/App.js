import React from 'react';
import {BrowserRouter as Router} from 'react-router-dom';
import {Provider} from 'react-redux'

import store from './store';
import Layout from './components/Layout'
import './App.css';

function App() {
  return (
    <div className="page-container">
      <div className="content-wrap">
        <Provider store={store}>
          <Router>
            <div className="App">
              <Layout/>
            </div>
          </Router>
        </Provider>
      </div>
    </div>
  );
}

export default App;
