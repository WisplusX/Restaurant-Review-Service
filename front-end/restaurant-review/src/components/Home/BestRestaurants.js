import React, { useEffect, useState } from 'react';
import { Box, Flex, Heading, Text } from '@chakra-ui/react';
import RestaurantCard from './RestaurantCard';

function BestRestaurants() {
  const [bestRestaurants, setBestRestaurants] = useState([]);

  useEffect(() => {
    async function fetchBestRestaurants() {
      try {
        const response = await fetch(
          'http://localhost:8080/api/restaurants?sortBy=avgRating&sortOrder=desc'
        );

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();

        const topThreeRestaurants = data.slice(0, 3);
        setBestRestaurants(topThreeRestaurants);
      } catch (error) {
        console.error('Error fetching best restaurants:', error);
      }
    }
    fetchBestRestaurants();
  }, []);

  return (
    <Box>
      <Heading as="h1" size="lg" mt="8">
        Лучшие рестораны
      </Heading>
      <Text fontSize="lg" mb="4">
        по оценкам пользователей
      </Text>

      <Flex flexWrap="wrap">
        {bestRestaurants.map(restaurant => (
          <RestaurantCard
            key={restaurant.id}
            id={restaurant.id}
            photo={restaurant.photo}
            cuisine={restaurant.cuisine}
            name={restaurant.name}
            avgRating={restaurant.avgRating}
            description={restaurant.description}
            priceRange={restaurant.priceRange}
          />
        ))}
      </Flex>
    </Box>
  );
}

export default BestRestaurants;
