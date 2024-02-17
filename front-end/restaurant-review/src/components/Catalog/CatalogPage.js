import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import {
  Box,
  Heading,
  Flex,
  Select,
  IconButton,
  Spinner,
  Text,
} from '@chakra-ui/react';
import { ArrowDownIcon, ArrowUpIcon } from '@chakra-ui/icons';
import RestaurantCard from '../Home/RestaurantCard';
import Header from '../Header';
import Footer from '../Footer';

function CatalogPage() {
  const { cuisine } = useParams();
  const [restaurants, setRestaurants] = useState([]);
  const [sortBy, setSortBy] = useState('defaultSort');
  const [filterBy, setFilterBy] = useState(cuisine || 'defaultFilter');
  const [sortOrder, setSortOrder] = useState('desc');
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchRestaurants = async () => {
      try {
        let url = 'http://localhost:8080/api/restaurants';
        if (sortBy !== 'defaultSort') {
          url += `?sortBy=${sortBy}&sortOrder=${sortOrder}`;
        } else {
          url += `?sortBy=avgRating&sortOrder=${sortOrder}`;
        }
        if (filterBy !== 'defaultFilter') {
          url += `&filterBy=${filterBy}`;
        }
        const response = await fetch(url);
        const data = await response.json();
        setRestaurants(data);
      } catch (error) {
        console.error('Error fetching restaurants:', error);
      } finally {
        setTimeout(() => {
          setIsLoading(false);
        }, 100);
      }
    };

    fetchRestaurants();
  }, [sortBy, filterBy, sortOrder, cuisine]);

  const handleSortChange = event => {
    setSortBy(event.target.value);
  };

  const handleFilterChange = event => {
    setFilterBy(event.target.value);
  };

  const toggleSortOrder = () => {
    setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
  };

  return (
    <Box>
      <Header />
      <Box maxWidth="1200px" mx="auto" minH="calc(100vh - 17em)">
        <Box ml="4" mr="4">
          <Flex align="center" justify="space-between" mt="12" mb="8">
            <Heading as="h1" size="lg">
              Каталог всех ресторанов
            </Heading>
            <Flex mr="4">
              <IconButton
                variant="outline"
                aria-label="Toggle sort order"
                size="lg"
                icon={sortOrder === 'asc' ? <ArrowUpIcon /> : <ArrowDownIcon />}
                onClick={toggleSortOrder}
              />
              <Select
                defaultValue="avgRating"
                ml="4"
                size="lg"
                onChange={handleSortChange}
              >
                <option value="avgRating">По рейтингу</option>
                <option value="priceRange">По цене</option>
              </Select>
              <Select
                defaultValue={cuisine}
                ml="4"
                size="lg"
                onChange={handleFilterChange}
              >
                <option value="defaultFilter">Любая кухня</option>
                <option value="Итальянская">Итальянская кухня</option>
                <option value="Французская">Французская кухня</option>
                <option value="Японская">Японская кухня</option>
                <option value="Мексиканская">Мексиканская кухня</option>
                <option value="Китайская">Китайская кухня</option>
              </Select>
            </Flex>
          </Flex>

          <Flex flexWrap="wrap">
            {isLoading ? (
              <Flex align="center" mx="auto" mt="10">
                <Spinner size="xl" color="black" />
              </Flex>
            ) : restaurants.length === 0 ? (
              <Text mx="auto" mt="10" fontSize="lg">
                Нет доступных ресторанов.
              </Text>
            ) : (
              restaurants.map(restaurant => (
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
              ))
            )}
          </Flex>
        </Box>
      </Box>
      <Footer />
    </Box>
  );
}

export default CatalogPage;
