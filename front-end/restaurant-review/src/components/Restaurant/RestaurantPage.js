import React, { useState, useEffect } from 'react';
import {
  Box,
  Heading,
  Text,
  Image,
  Icon,
  Flex,
  Alert,
  AlertIcon,
  AlertTitle,
  AlertDescription,
  Button,
} from '@chakra-ui/react';
import {
  FaUtensils,
  FaStar,
  FaMoneyBill,
  FaMapMarkerAlt,
} from 'react-icons/fa';
import Header from '../Header';
import ReviewCard from './ReviewCard';
import { useParams } from 'react-router-dom';
import ReviewFormPopup from './ReviewFormPopup';
import Footer from '../Footer';

function RestaurantPage() {
  const [restaurant, setRestaurant] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isPopupOpen, setPopupOpen] = useState(false);

  const { id } = useParams();

  useEffect(() => {
    async function fetchRestaurant() {
      try {
        const response = await fetch(
          `http://localhost:8080/api/restaurants/${id}`
        );
        if (!response.ok) {
          throw new Error('Failed to fetch restaurant');
        }
        const data = await response.json();
        setRestaurant(data);
      } catch (error) {
        console.error('Error fetching restaurant:', error);
      }
    }

    async function fetchReviews() {
      try {
        const response = await fetch(
          `http://localhost:8080/api/reviews/restaurant/${id}`
        );
        if (!response.ok) {
          throw new Error('Failed to fetch reviews');
        }
        const data = await response.json();
        setReviews(data);
      } catch (error) {
        console.error('Error fetching reviews:', error);
      } finally {
        setIsLoading(false);
      }
    }

    fetchRestaurant();
    fetchReviews();
  }, [id]);

  const togglePopup = () => {
    setPopupOpen(!isPopupOpen);
  };

  if (isLoading) {
    return <Header />;
  }

  if (!restaurant && !isLoading) {
    return (
      <Alert status="error">
        <AlertIcon />
        <AlertTitle>Ошибка!</AlertTitle>
        <AlertDescription>
          Не удалось загрузить данные о ресторане.
        </AlertDescription>
      </Alert>
    );
  }

  return (
    <Box>
      <Header />
      <Box maxWidth="1200px" mx="auto">
        <Box ml="4" mr="8">
          <Image
            src={`/resources/restaurant-photos/${restaurant.photo}`}
            alt="Restaurant"
            w="100%"
            h="500px"
            objectFit="cover"
            my="10"
            borderRadius="xl"
          />
          <Flex justify="space-between">
            <Box w="70%" mr="10">
              <Box mb="4">
                <Heading as="h1" size="xl">
                  {restaurant.name}
                </Heading>
                <Text fontSize="lg" mt="3">
                  {restaurant.description}
                </Text>
              </Box>
              <Box mb="4">
                <Flex mb="2">
                  <Icon as={FaMapMarkerAlt} color="green.400" boxSize="8" />
                  <Box ml="4">
                    <Heading as="h2" size="md">
                      Расположение:
                    </Heading>
                    <Text>{restaurant.location}</Text>
                  </Box>
                </Flex>
              </Box>
              <Flex direction="column" mb="8" mt="16">
                <Flex mb="4" align="center" justifyContent="space-between">
                  <Heading as="h2" size="lg">
                    Отзывы
                  </Heading>
                  <Button
                    bgColor="green.400"
                    color="white"
                    onClick={togglePopup}
                    fontWeight="normal"
                    _hover={{ bgColor: 'green.100', color: 'green.400' }}
                  >
                    Добавить отзыв
                  </Button>
                </Flex>

                <Flex direction="column">
                  <ReviewFormPopup
                    isOpen={isPopupOpen}
                    onClose={togglePopup}
                    onSubmit={review => {
                      console.log(review);
                    }}
                    restaurantId={id}
                  />
                </Flex>
                {reviews.length > 0 ? (
                  reviews.map((review, index) => (
                    <ReviewCard
                      key={index}
                      reviewer={review.authorName}
                      rating={review.rating}
                      comment={review.text}
                      date={review.creationDate}
                    />
                  ))
                ) : (
                  <Text color="gray.500">Отзывов нет. Станьте первым!</Text>
                )}
              </Flex>
            </Box>
            <Box
              bg="gray.100"
              p="8"
              w="300px"
              borderRadius="xl"
              h="fit-content"
            >
              <Box mb="4">
                <Flex align="center">
                  <Icon as={FaUtensils} color="green.400" />
                  <Text fontSize="lg" ml="2" fontWeight="bold">
                    Тип кухни:
                  </Text>
                </Flex>
                <Text>{restaurant.cuisine}</Text>
              </Box>
              <Box mb="4">
                <Flex align="center">
                  <Icon as={FaStar} color="yellow.400" />
                  <Text fontSize="lg" ml="2" fontWeight="bold">
                    Рейтинг:
                  </Text>
                </Flex>
                <Text>
                  {restaurant.avgRating === 0
                    ? '(Отзывов нет)'
                    : `${restaurant.avgRating.toFixed(1)} ${
                        restaurant.avgRating >= 4.7
                          ? '(Превосходно)'
                          : restaurant.avgRating >= 4.3
                          ? '(Отлично)'
                          : restaurant.avgRating >= 3.9
                          ? '(Хорошо)'
                          : restaurant.avgRating >= 3
                          ? '(Неплохо)'
                          : restaurant.avgRating >= 2.4
                          ? '(Плохо)'
                          : '(Ужасно)'
                      }`}
                </Text>
              </Box>
              <Box>
                <Flex align="center">
                  <Icon as={FaMoneyBill} color="blue.400" />
                  <Text fontSize="lg" ml="2" fontWeight="bold">
                    Ценовой диапазон:
                  </Text>
                </Flex>
                <Text>
                  {restaurant.priceRange}{' '}
                  {restaurant.priceRange === '$$$$'
                    ? '(Дорого)'
                    : restaurant.priceRange === '$$$'
                    ? '(Средне)'
                    : restaurant.priceRange === '$$'
                    ? '(Недорого)'
                    : '(Дешево)'}
                </Text>
              </Box>
            </Box>
          </Flex>
        </Box>
      </Box>
      <Footer />
    </Box>
  );
}

export default RestaurantPage;
