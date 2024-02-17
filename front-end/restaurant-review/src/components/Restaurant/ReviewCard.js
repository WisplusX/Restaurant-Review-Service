import { Box, Flex, Text, Icon } from '@chakra-ui/react';
import { FaStar } from 'react-icons/fa';

function formatDate(dateString) {
  const date = new Date(dateString);
  const day = date.getDate().toString().padStart(2, '0');
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const year = date.getFullYear();
  return `${day}.${month}.${year}`;
}

function ReviewCard({ reviewer, rating, comment, date }) {
  const renderStars = () => {
    const stars = [];
    for (let i = 0; i < 5; i++) {
      if (i < rating) {
        stars.push(<Icon as={FaStar} color="green.400" key={i} />);
      } else {
        stars.push(<Icon as={FaStar} color="gray.500" key={i} />);
      }
    }
    return stars;
  };

  return (
    <Box bg="gray.100" p="4" borderRadius="xl" mb="4" alignItems="center">
      <Text fontSize="lg" fontWeight="bold" mb="1">
        {reviewer}
      </Text>
      <Flex alignItems="center" mb="1">
        <Box mr="2">{renderStars()}</Box>
        <Text fontSize="sm" color="gray.500" mb="0.5">
          {formatDate(date)}
        </Text>
      </Flex>
      <Text>
        {comment ? comment : <Text color="gray.500">(Отзыв без текста)</Text>}
      </Text>
    </Box>
  );
}

export default ReviewCard;
