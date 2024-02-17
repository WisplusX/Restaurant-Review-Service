import React from 'react';
import { Box } from '@chakra-ui/react';
import Header from '../Header';
import Footer from '../Footer';
import BestRestaurants from './BestRestaurants';
import ChooseCuisine from './ChooseCuisine';
import PopularRestaurants from './PopularRestaurants';

function HomePage() {
  return (
    <Box>
      <Header />
      <Box maxWidth="1200px" mx="auto" minH="calc(100vh - 17em)">
        <Box ml="4" mr="4">
          <ChooseCuisine />
          <BestRestaurants />
          <PopularRestaurants />
        </Box>
      </Box>
      <Footer />
    </Box>
  );
}

export default HomePage;
