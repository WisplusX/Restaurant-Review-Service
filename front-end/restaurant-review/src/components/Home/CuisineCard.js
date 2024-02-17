import React from 'react';
import { Box, Text, Link as ChakraLink } from '@chakra-ui/react';
import { Link } from 'react-router-dom';

function CuisineCard({ image, caption }) {
  return (
    <ChakraLink
      as={Link}
      to={`/catalog/${caption}`}
      style={{ textDecoration: 'none' }}
      overflow="hidden"
      mr="4"
      mb="4"
      width={{
        base: '100%',
        sm: 'calc(50% - 16px)',
        md: 'calc(33.33% - 16px)',
        lg: 'calc(20% - 16px)',
      }}
    >
      <Box position="relative" borderRadius="xl" overflow="hidden">
        <img
          src={image}
          alt={caption}
          style={{
            width: '100%',
            height: '150px',
            objectFit: 'cover',
          }}
        />
      </Box>
      <Text fontSize="lg" fontWeight="medium" mt="2">
        {caption}
      </Text>
    </ChakraLink>
  );
}

export default CuisineCard;
