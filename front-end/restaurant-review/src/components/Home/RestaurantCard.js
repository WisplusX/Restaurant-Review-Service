import React from 'react';
import { Box, Text, Flex, Icon, Link as ChakraLink } from '@chakra-ui/react';
import { FaStar } from 'react-icons/fa'; // Иконка звезды
import { Link } from 'react-router-dom';

function RestaurantCard({
  id,
  photo,
  cuisine,
  name,
  avgRating,
  description,
  priceRange,
}) {
  return (
    <ChakraLink
      as={Link}
      to={`/restaurants/${id}`}
      style={{ textDecoration: 'none' }}
      width={{
        base: '100%',
        sm: 'calc(100% - 16px)',
        md: 'calc(33.33% - 16px)',
      }}
      overflow="hidden"
      position="relative" // Для позиционирования элементов на подложке
      borderRadius="xl" // Скругление углов карточки
      mb="4" // Отступ снизу
      mr="4"
      bg="gray.100"
    >
      <Box>
        {/* Изображение сверху */}
        <img
          src={`/resources/restaurant-photos/${photo}`}
          alt={name}
          style={{
            width: '100%',
            height: '200px',
            objectFit: 'cover',
          }}
        />

        {/* Подложка с текстом */}
        <Box p="4">
          {/* Верхняя часть текста */}
          <Text fontSize="sm" color="rgba(0,0,0,0.8)">
            {cuisine} &bull; {priceRange}
          </Text>

          {/* Название ресторана и рейтинг */}
          <Flex align="center" mt="1">
            <Text fontSize="xl" fontWeight="bold" noOfLines={1}>
              {name}
            </Text>
            <Flex align="center">
              <Icon as={FaStar} color="green.400" ml="3" />
              <Text ml="1" mt="0.5">
                {avgRating !== 0 ? (
                  avgRating.toFixed(1)
                ) : (
                  <Text fontSize="sm" color="gray.500">
                    Отзывов нет
                  </Text>
                )}
              </Text>
            </Flex>
          </Flex>

          {/* Описание ресторана */}
          <Text fontSize="md" noOfLines={2}>
            {description}
          </Text>
        </Box>
      </Box>
    </ChakraLink>
  );
}

export default RestaurantCard;
