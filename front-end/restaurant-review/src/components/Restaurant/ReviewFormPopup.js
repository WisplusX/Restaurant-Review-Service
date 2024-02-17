import { useState } from 'react';
import {
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalCloseButton,
  ModalBody,
  ModalFooter,
  Button,
  Input,
  Textarea,
  Divider,
  Flex,
  Icon,
  FormErrorMessage,
  FormControl,
} from '@chakra-ui/react';
import { FaStar } from 'react-icons/fa';

function ReviewFormPopup({ isOpen, onClose, onSubmit, restaurantId }) {
  const [reviewer, setReviewer] = useState('');
  const [email, setEmail] = useState('');
  const [rating, setRating] = useState(0);
  const [comment, setComment] = useState('');
  const [hoveredRating, setHoveredRating] = useState(0);
  const [reviewerError, setReviewerError] = useState('');
  const [emailError, setEmailError] = useState('');
  const [ratingError, setRatingError] = useState('');

  const validateEmail = () => {
    const isValid = /\S+@\S+\.\S+/.test(email);
    return isValid;
  };

  const handleSubmit = async () => {
    // Проверка на заполненность полей
    if (!reviewer || rating === 0 || !validateEmail()) {
      setReviewerError(reviewer ? '' : 'Введите ваше имя');
      setRatingError(rating !== 0 ? '' : 'Выберите рейтинг');
      setEmailError(validateEmail() ? '' : 'Введите корректный email');
      return;
    }

    try {
      // Отправка отзыва на сервер
      const response = await fetch(
        `http://localhost:8080/api/reviews?restaurantId=${restaurantId}`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            text: comment,
            rating,
            authorName: reviewer,
            authorEmail: email,
          }),
        }
      );
      if (!response.ok) {
        throw new Error('Failed to send review');
      }

      // Отправка данных формы наружу через функцию onSubmit
      onSubmit({ reviewer, email, rating, comment });

      // Сброс значений полей
      setReviewer('');
      setEmail('');
      setRating(0);
      setComment('');

      // Закрытие поп-апа
      onClose();
    } catch (error) {
      console.error('Error sending review:', error);
      // Обработка ошибки отправки отзыва
    }
  };

  const renderStars = () => {
    const stars = [];
    for (let i = 1; i <= 5; i++) {
      stars.push(
        <Icon
          as={FaStar}
          boxSize={8}
          color={(hoveredRating || rating) >= i ? 'yellow.500' : 'gray.300'}
          key={i}
          onMouseEnter={() => setHoveredRating(i)}
          onMouseLeave={() => setHoveredRating(0)}
          onClick={() => setRating(i)}
          cursor="pointer"
        />
      );
    }
    return stars;
  };

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <ModalOverlay />
      <ModalContent p="2">
        <ModalHeader>Написать отзыв</ModalHeader>
        <ModalCloseButton mt="4" mr="3" />
        <Divider />
        <ModalBody>
          <FormControl isInvalid={rating === 0} mb="4" mt="3">
            <Flex alignItems="center" justifyContent="center" mx="8">
              <Flex flexGrow={1} justifyContent="space-between">
                {renderStars()}
              </Flex>
            </Flex>
            <FormErrorMessage>{ratingError}</FormErrorMessage>
          </FormControl>
          <FormControl isInvalid={!!reviewerError} mb="2">
            <Input
              placeholder="Ваше имя"
              value={reviewer}
              onChange={e => setReviewer(e.target.value)}
            />
            <FormErrorMessage>{reviewerError}</FormErrorMessage>
          </FormControl>
          <FormControl isInvalid={!!emailError} mb="2">
            <Input
              type="email"
              placeholder="Ваш email"
              value={email}
              onChange={e => setEmail(e.target.value)}
            />
            <FormErrorMessage>{emailError}</FormErrorMessage>
          </FormControl>
          <Textarea
            placeholder="Ваш отзыв"
            value={comment}
            onChange={e => setComment(e.target.value)}
          />
        </ModalBody>

        <ModalFooter>
          <Button
            bgColor="green.400"
            fontWeight="normal"
            color="white"
            _hover={{ bgColor: 'green.100', color: 'green.400' }}
            onClick={handleSubmit}
          >
            Отправить
          </Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
}

export default ReviewFormPopup;
