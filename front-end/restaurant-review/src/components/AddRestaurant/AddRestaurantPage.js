import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Box,
  FormControl,
  FormErrorMessage,
  Input,
  Textarea,
  Button,
  Select,
  Text,
  useToast,
} from '@chakra-ui/react';
import Header from '../Header';

function AddRestaurantPage() {
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [location, setLocation] = useState('');
  const [cuisine, setCuisine] = useState('');
  const [priceRange, setPriceRange] = useState('');
  const [nameError, setNameError] = useState('');
  const [descriptionError, setDescriptionError] = useState('');
  const [locationError, setLocationError] = useState('');
  const [cuisineError, setCuisineError] = useState('');
  const [priceRangeError, setPriceRangeError] = useState('');
  const [selectedFile, setSelectedFile] = useState(null);
  const [selectedFileError, setSelectedFileError] = useState(null);
  const toast = useToast();

  const handleFileChange = e => {
    setSelectedFile(e.target.files[0]);
  };

  const handleSubmit = () => {
    let hasError = false;

    if (!name) {
      setNameError('Пожалуйста, введите название ресторана');
      hasError = true;
    } else {
      setNameError('');
    }

    if (!description) {
      setDescriptionError('Пожалуйста, введите описание');
      hasError = true;
    } else {
      setDescriptionError('');
    }

    if (!location) {
      setLocationError('Пожалуйста, введите местоположение');
      hasError = true;
    } else {
      setLocationError('');
    }

    if (!cuisine) {
      setCuisineError('Пожалуйста, выберите тип кухни');
      hasError = true;
    } else {
      setCuisineError('');
    }

    if (!priceRange) {
      setPriceRangeError('Пожалуйста, выберите диапазон цен');
      hasError = true;
    } else {
      setPriceRangeError('');
    }

    if (!selectedFile) {
      setSelectedFileError('Пожалуйста, выберите фото');
      hasError = true;
    } else {
      setPriceRangeError('');
    }

    if (hasError) {
      return;
    }

    if (selectedFile && !hasError) {
      const formData = new FormData();
      formData.append('file', selectedFile);

      // Отправляем информацию о ресторане на сервер
      const restaurantData = {
        name,
        description,
        location,
        cuisine,
        priceRange,
        photo: selectedFile.name,
      };

      fetch('http://localhost:8080/api/restaurants', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(restaurantData),
      })
        .then(restaurantResponse => {
          if (!restaurantResponse.ok) {
            throw new Error('Failed to add restaurant');
          }
          console.log('Restaurant added successfully');

          // После успешного сохранения ресторана, загружаем фото
          fetch('http://localhost:8080/resources/restaurant-photos', {
            method: 'POST',
            body: formData,
          })
            .then(photoResponse => {
              if (!photoResponse.ok) {
                throw new Error('Failed to upload photo');
              }
              console.log('Photo uploaded successfully');
              navigate('/catalog');
            })
            .catch(error => {
              console.error('Error uploading photo:', error);
            });
        })
        .catch(error => {
          console.error('Error adding restaurant:', error);
        });
    }
  };

  return (
    <Box>
      <Header />
      <Box maxWidth="600px" mx="auto" mt="16">
        <FormControl isInvalid={!!nameError} mb="2">
          <Input
            placeholder="Название ресторана"
            value={name}
            onChange={e => setName(e.target.value)}
          />
          <FormErrorMessage>{nameError}</FormErrorMessage>
        </FormControl>

        <FormControl isInvalid={!!descriptionError} mb="2">
          <Textarea
            placeholder="Описание"
            value={description}
            onChange={e => setDescription(e.target.value)}
          />
          <FormErrorMessage>{descriptionError}</FormErrorMessage>
        </FormControl>

        <FormControl isInvalid={!!locationError} mb="2">
          <Input
            placeholder="Местоположение"
            value={location}
            onChange={e => setLocation(e.target.value)}
          />
          <FormErrorMessage>{locationError}</FormErrorMessage>
        </FormControl>

        <FormControl isInvalid={!!cuisineError} mb="2">
          <Select
            placeholder="Тип кухни"
            value={cuisine}
            onChange={e => setCuisine(e.target.value)}
          >
            <option value="Итальянская">Итальянская кухня</option>
            <option value="Французская">Французская кухня</option>
            <option value="Японская">Японская кухня</option>
            <option value="Мексиканская">Мексиканская кухня</option>
            <option value="Китайская">Китайская кухня</option>
          </Select>
          <FormErrorMessage>{cuisineError}</FormErrorMessage>
        </FormControl>

        <FormControl isInvalid={!!priceRangeError} mb="2">
          <Select
            placeholder="Диапазон цен"
            value={priceRange}
            onChange={e => setPriceRange(e.target.value)}
          >
            <option value="$">Дешево</option>
            <option value="$$">Недорого</option>
            <option value="$$$">Средне</option>
            <option value="$$$$">Дорого</option>
          </Select>
          <FormErrorMessage>{priceRangeError}</FormErrorMessage>
        </FormControl>

        <FormControl isInvalid={!!selectedFileError} mb="4" mt="4">
          <Input
            type="file"
            variant="unstyled"
            borderRadius="0"
            onChange={handleFileChange}
          />
          <FormErrorMessage>Пожалуйста, выберите фото</FormErrorMessage>
        </FormControl>

        <Button
          bgColor="green.400"
          fontWeight="normal"
          color="white"
          _hover={{ bgColor: 'green.100', color: 'green.400' }}
          onClick={handleSubmit}
        >
          Добавить ресторан
        </Button>

        <Text fontSize="sm" color="gray.500" mt="2">
          *Все поля обязательны.
        </Text>
      </Box>
    </Box>
  );
}

export default AddRestaurantPage;
