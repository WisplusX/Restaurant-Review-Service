import React from 'react';
import { ChakraProvider } from '@chakra-ui/react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import theme from './theme';

import HomePage from './components/Home/HomePage';
import CatalogPage from './components/Catalog/CatalogPage';
import RestaurantPage from './components/Restaurant/RestaurantPage';
import AddRestaurantPage from './components/AddRestaurant/AddRestaurantPage';

function App() {
  return (
    <ChakraProvider theme={theme}>
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/catalog" element={<CatalogPage />} />
          <Route path="/catalog/add" element={<AddRestaurantPage />} />
          <Route path="/catalog/:cuisine" element={<CatalogPage />} />
          <Route path="/restaurants/:id" element={<RestaurantPage />} />
        </Routes>
      </Router>
    </ChakraProvider>
  );
}

export default App;
