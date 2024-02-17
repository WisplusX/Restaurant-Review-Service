import React, { useEffect, useState } from 'react';
import { Box, Flex, Heading, Text } from '@chakra-ui/react';
import RestaurantCard from './RestaurantCard';

function PopularRestaurants() {
  const [popularRestaurants, setPopularRestaurants] = useState([]);

  useEffect(() => {
    async function fetchPopularRestaurants() {
      try {
        const response = await fetch(
          'http://localhost:8080/api/restaurants/popular'
        );

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();

        const topThreeRestaurants = data.slice(0, 3);
        setPopularRestaurants(topThreeRestaurants);
      } catch (error) {
        console.error('Error fetching popular restaurants:', error);
      }
    }
    fetchPopularRestaurants();
  }, []);

  return (
    <Box>
      <Heading as="h1" size="lg" mt="8">
        Самые популярные рестораны
      </Heading>
      <Text fontSize="lg" mb="4">
        по количеству оценок пользователей
      </Text>

      <Flex flexWrap="wrap">
        {popularRestaurants.map(restaurant => (
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

export default PopularRestaurants;
