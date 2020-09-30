-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 21, 2020 at 11:50 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_asset_teddy`
--

-- --------------------------------------------------------

--
-- Table structure for table `assets`
--

CREATE TABLE `assets` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `serial_number` int(11) NOT NULL,
  `barcode` bigint(20) NOT NULL,
  `asset_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `location` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `department` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_by_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_by_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `assets`
--

INSERT INTO `assets` (`id`, `serial_number`, `barcode`, `asset_type`, `description`, `location`, `department`, `status`, `created_by_user`, `updated_by_user`, `remark`, `created_at`, `updated_at`) VALUES
(1, 34435, 756781645125, 'Monitor', 'super monitor', 'Axiata Tower 9th Floor', 'HRD', 'Assigned', 'dmitryhunter', NULL, 'remark Test', '2020-09-15 10:39:36', '2020-09-17 21:27:50'),
(3, 567567, 1234567890, 'Modem', 'modelm description', 'modem location', 'modem depart ment', 'Faulty', 'dmitryhunter', NULL, 'modem remark', '2020-09-15 11:56:16', '2020-09-15 11:56:16'),
(5, 45645654, 725272730706, 'Monitor', 'dsgsdfg', 'Axiata Tower 9th Floor', 'HRD', 'Assigned', 'dmitryhunter', NULL, 'dghgdgd', '2020-09-15 18:20:32', '2020-09-15 18:20:32'),
(7, 34654354, 9876543210981, 'Monitor', 'dfhg', 'Axiata Tower 9th Floor', 'HRD', 'Assigned', 'dmitryhunter', 'dmitryhunter', 'sdfdsf', '2020-09-15 22:13:26', '2020-09-17 21:42:22'),
(10, 52365, 75678164125, 'CPU', 'mydescription', 'Axiata Tower 9th Floor', 'HRD', 'Assigned', 'dmitryhunter', NULL, '123456', '2020-09-17 18:03:43', '2020-09-17 18:03:43'),
(11, 555, 987654321098, 'Mouse', 'bestMouse', '1 Sentral Level 6', 'QD', 'Faulty', 'dmitryhunter', 'dmitryhunter', 'bestTest', '2020-09-20 08:20:15', '2020-09-20 08:20:15');

-- --------------------------------------------------------

--
-- Table structure for table `asset_status_category`
--

CREATE TABLE `asset_status_category` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `asset_type_category`
--

CREATE TABLE `asset_type_category` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `asset_type_category`
--

INSERT INTO `asset_type_category` (`id`, `name`, `created_at`, `updated_at`) VALUES
(1, 'Assigned', NULL, NULL),
(2, 'Unassign', NULL, NULL),
(3, 'Faulty', NULL, NULL),
(4, 'Dispose', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2014_10_12_000000_create_users_table', 1),
(2, '2016_06_01_000001_create_oauth_auth_codes_table', 1),
(3, '2016_06_01_000002_create_oauth_access_tokens_table', 1),
(4, '2016_06_01_000003_create_oauth_refresh_tokens_table', 1),
(5, '2016_06_01_000004_create_oauth_clients_table', 1),
(6, '2016_06_01_000005_create_oauth_personal_access_clients_table', 1),
(7, '2020_08_18_191403_create_assets_table', 1),
(8, '2020_08_18_192612_create_asset_type_category_table', 1),
(9, '2020_08_19_023622_create_asset_status_category_table', 1);

-- --------------------------------------------------------

--
-- Table structure for table `oauth_access_tokens`
--

CREATE TABLE `oauth_access_tokens` (
  `id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `client_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `scopes` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `revoked` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `expires_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `oauth_access_tokens`
--

INSERT INTO `oauth_access_tokens` (`id`, `user_id`, `client_id`, `name`, `scopes`, `revoked`, `created_at`, `updated_at`, `expires_at`) VALUES
('15a202f366fa3a87cedc0542296eb084c112587486fbd9cfa588a7042c4b354c492472d424b47ed3', 9, 1, 'MyApp', '[]', 0, '2020-09-20 09:51:16', '2020-09-20 09:51:16', '2021-09-20 18:51:16'),
('1ec22ef2fc9acd23255dc4be8fb888146cd8aa47f23cbfaa733b050f3178cab6909b4516d449a4e6', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:16:43', '2020-09-20 15:16:43', '2021-09-21 00:16:43'),
('222b21369d4bf349fffacc923eb14b111f7cf027b54065dfd5d595b19e9cfaea3a53933d6049f313', 1, 1, 'MyApp', '[]', 0, '2020-09-20 09:50:33', '2020-09-20 09:50:33', '2021-09-20 18:50:33'),
('27a34119295fb41304ef3b2f8fc07ec1cef1a9361589103d5d414cb31917f5248f320b7bb0f8d0aa', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:26:30', '2020-09-20 15:26:30', '2021-09-21 00:26:30'),
('2eb486bfa87866f18daaecf27f649b158b3f54df494008087f1b2b359bcd24ab7774c064a6bd393d', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:40:48', '2020-09-20 15:40:48', '2021-09-21 00:40:48'),
('350b4f2b75f9d0bc6abeb07553187ddd7727407a533d2be2b3e312f35cbf847752712d14f64dcdc9', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:12:09', '2020-09-20 15:12:09', '2021-09-21 00:12:09'),
('3725236dbe21182e9cfe20aa75e7729a4f993edb3504163378726f9896e5a0e0d275405850d6e449', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:35:15', '2020-09-20 15:35:15', '2021-09-21 00:35:15'),
('39ae52fbca064bfe5ae165d4951db4c2fd037e874d292e0f73e1e1be6457ce03d51a0fc5c78094b5', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:37:09', '2020-09-20 15:37:09', '2021-09-21 00:37:09'),
('3afc9ca9b16d8a8037733ca18af458120594a9c217ebddac9251667dac962a3f90f3a61c93440e08', 2, 1, 'MyApp', '[]', 0, '2020-09-09 18:22:46', '2020-09-09 18:22:46', '2021-09-10 03:22:46'),
('4595b0183737daa6d0a22b4a5cfd6a7173acc9862b4b0b08b39d92f9b534cd634d21c506bc2d7e57', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:28:16', '2020-09-20 15:28:16', '2021-09-21 00:28:16'),
('46c0c9b2d520c41a177b56de5b501a2b9f8fb3feb8b2666440db49f6f0dc9d1c53a99e10c33bb136', 1, 1, 'MyApp', '[]', 0, '2020-09-20 08:32:17', '2020-09-20 08:32:17', '2021-09-20 17:32:17'),
('559cc9909d0316fcd44f876235d2ba5cfb6cf88297a4f9cc16fc63ca530f99212356c7ba28eace78', 1, 1, 'MyApp', '[]', 0, '2020-09-20 11:40:00', '2020-09-20 11:40:00', '2021-09-20 20:40:00'),
('57f6db7fb9e8e4d3def5bd92738e80d91833fd14cf954631fd20b8c93cc001effb49da20caf1746e', 1, 1, 'MyApp', '[]', 0, '2020-09-20 08:43:36', '2020-09-20 08:43:36', '2021-09-20 17:43:36'),
('5cb416ecff66d11be2585550e2daeb8f9e882524318c67594219e45be8f38f54c506f717eb477566', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:11:00', '2020-09-20 15:11:00', '2021-09-21 00:11:00'),
('694cead4355a93b5979a9a9d7ef035efbfb483422f2908c596284b73ad18eace65db57ade7b7277c', 2, 1, 'MyApp', '[]', 0, '2020-09-11 14:53:07', '2020-09-11 14:53:07', '2021-09-11 23:53:07'),
('70eeccaf05feb83d058a29444b70f69b86023c454df5f5384307f03bd2b44515a7d6dbd8f2ab762a', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:32:39', '2020-09-20 15:32:39', '2021-09-21 00:32:39'),
('83d72493831a7f2b5ee226f33b896ebc8eb471a4612b449d71b6a9b10493212bdda59dcfc6d997d3', 1, 1, 'MyApp', '[]', 0, '2020-09-20 11:13:19', '2020-09-20 11:13:19', '2021-09-20 20:13:19'),
('882bbed447e1f4ad0067ab911bdb85785c0cc141c87351ec832fb74504bf3f8b7d60325d045d5206', 1, 1, 'MyApp', '[]', 0, '2020-09-20 11:34:49', '2020-09-20 11:34:49', '2021-09-20 20:34:49'),
('9cdea756f7189d8d17d7bfd36945abec7b212e71a278d2f2b4d73ebfafc1a3fc16e90c4a787e2a8a', 1, 1, 'MyApp', '[]', 0, '2020-09-20 08:46:04', '2020-09-20 08:46:04', '2021-09-20 17:46:04'),
('a4b188fa5bf6c4c40f808287f77678b8f414ed640d60017b7a2a179c001650cfc5b26820ebb9ef9f', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:42:10', '2020-09-20 15:42:10', '2021-09-21 00:42:10'),
('a652ca44be3f43de6dd084f6fe4ede39d4c8b939a5158b06d498325cb38d39a622664d8653b1676a', 1, 1, 'MyApp', '[]', 0, '2020-09-20 08:35:27', '2020-09-20 08:35:27', '2021-09-20 17:35:27'),
('ac5364c5106c2e014e6b129d45e86625295fde1ee6a7dbb9e502b1d7151b66dc1d90fab16964db4d', 2, 1, 'MyApp', '[]', 0, '2020-09-04 00:35:47', '2020-09-04 00:35:47', '2021-09-04 09:35:47'),
('b305f9d79a415a8fbfde4575eae6a066146b085c4ae9d8c060d6009b9071af4694cb672092551bba', 1, 1, 'MyApp', '[]', 0, '2020-09-20 11:15:41', '2020-09-20 11:15:41', '2021-09-20 20:15:41'),
('bb9b127e0bffb04d8e50ab9b1e0d1174357437ac3395a22e45f54b23d1255f184e8159a6c6f3d9fa', 1, 1, 'MyApp', '[]', 0, '2020-09-20 08:49:24', '2020-09-20 08:49:24', '2021-09-20 17:49:24'),
('c1887271b180a8b2875e5cd53e263f9c0f4ef3346929a4cc6aba09a516181b69e806461a4c78dd73', 1, 1, 'MyApp', '[]', 0, '2020-09-20 11:36:52', '2020-09-20 11:36:52', '2021-09-20 20:36:52'),
('c57c8b4a9f726d851ba3bf5e4dc6e0d7009d4535872a850d19e18cfb17979be1062dc72987167447', 1, 1, 'MyApp', '[]', 0, '2020-09-20 09:13:58', '2020-09-20 09:13:58', '2021-09-20 18:13:58'),
('c76ecede5503bf9b8a3c78a2d7133486e4a8d8d49c0a697f1455d01821c4e8d38515dbcaa84d4bd3', 1, 1, 'MyApp', '[]', 0, '2020-09-20 09:24:00', '2020-09-20 09:24:00', '2021-09-20 18:24:00'),
('c992754a5cb6300f3a6b9ab1f6cf8d0bbae9c3814f7b62cc64e65cf0b04c6e82dd791677e19cb36b', 1, 1, 'MyApp', '[]', 0, '2020-09-20 08:20:32', '2020-09-20 08:20:32', '2021-09-20 17:20:32'),
('d16c2170c5b7d528a2bb1d66d13996bb97bc0ac60187dd604723df228bc0f12e18cb113703160fc4', 1, 1, 'MyApp', '[]', 0, '2020-09-20 08:31:24', '2020-09-20 08:31:24', '2021-09-20 17:31:24'),
('d8874f49a0cad68ee45694d39c62b2b30500643eb26778d6dedef8223d466748d01a085e63676308', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:34:14', '2020-09-20 15:34:14', '2021-09-21 00:34:14'),
('dd9bee08f63b5a933c636e4edae07a00527f6add7a88edf776d7ba64742351097e7355a9dbb6c033', 9, 1, 'MyApp', '[]', 0, '2020-09-20 17:03:10', '2020-09-20 17:03:10', '2021-09-21 02:03:10'),
('de64cd8bbce7389953f8ee08f8de9f4ea6c3ce142b0bd8ba7245639ff845c72fb69f5b604fcc1e73', 1, 1, 'MyApp', '[]', 0, '2020-09-20 08:54:36', '2020-09-20 08:54:36', '2021-09-20 17:54:36'),
('ef6a50dc1941736ed7cca09780a96a17fe22cf5a72934ba484324609fd76adf0b7fd6a5a0d0038f8', 1, 1, 'MyApp', '[]', 0, '2020-09-20 09:11:39', '2020-09-20 09:11:39', '2021-09-20 18:11:39'),
('efdc63745e6fca3c976e857689912baf084735b6e34be99760f2e157fbd28bd6142491bc944ccaef', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:17:58', '2020-09-20 15:17:58', '2021-09-21 00:17:58'),
('f0e883d0d284fbdaa7413d3c5e3008d8c8a272540fa0ae11d0f6b8b21647a801d5ea07fef878a214', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:37:52', '2020-09-20 15:37:52', '2021-09-21 00:37:52'),
('f96ced5bad4ed91579a7b06632346db4cadbbf96e3d2280cbd71f62b6a1b31dd5b1551573a0c66b5', 1, 1, 'MyApp', '[]', 0, '2020-09-20 15:30:54', '2020-09-20 15:30:54', '2021-09-21 00:30:54'),
('f9dfce9db7116edc8dd2d33a77bef0947e7776098d2adb8a5d312368548b105a717b4c5c415a38f3', 1, 1, 'MyApp', '[]', 0, '2020-09-20 11:29:22', '2020-09-20 11:29:22', '2021-09-20 20:29:22');

-- --------------------------------------------------------

--
-- Table structure for table `oauth_auth_codes`
--

CREATE TABLE `oauth_auth_codes` (
  `id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `client_id` bigint(20) UNSIGNED NOT NULL,
  `scopes` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expires_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `oauth_clients`
--

CREATE TABLE `oauth_clients` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `secret` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `provider` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `redirect` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `personal_access_client` tinyint(1) NOT NULL,
  `password_client` tinyint(1) NOT NULL,
  `revoked` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `oauth_clients`
--

INSERT INTO `oauth_clients` (`id`, `user_id`, `name`, `secret`, `provider`, `redirect`, `personal_access_client`, `password_client`, `revoked`, `created_at`, `updated_at`) VALUES
(1, NULL, 'Quick Pass Personal Access Client', '3oqteJt65mtEGjRCCqxDvNwp5w45bukxK1AuXKs3', NULL, 'http://localhost', 1, 0, 0, '2020-09-02 04:06:45', '2020-09-02 04:06:45'),
(2, NULL, 'Quick Pass Password Grant Client', 'RsrDDKs4Hq4P6ihAKEBkHjmLYFn76nz0MMLNMKdi', 'users', 'http://localhost', 0, 1, 0, '2020-09-02 04:06:46', '2020-09-02 04:06:46');

-- --------------------------------------------------------

--
-- Table structure for table `oauth_personal_access_clients`
--

CREATE TABLE `oauth_personal_access_clients` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `client_id` bigint(20) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `oauth_personal_access_clients`
--

INSERT INTO `oauth_personal_access_clients` (`id`, `client_id`, `created_at`, `updated_at`) VALUES
(1, 1, '2020-09-02 04:06:45', '2020-09-02 04:06:45');

-- --------------------------------------------------------

--
-- Table structure for table `oauth_refresh_tokens`
--

CREATE TABLE `oauth_refresh_tokens` (
  `id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `access_token_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expires_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `confirm_password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `contact_number` int(11) NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `username`, `email`, `password`, `confirm_password`, `gender`, `contact_number`, `address`, `status`, `role`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'Andy', 'andy', 'andykooyeedeck@gmail.com', '$2y$10$Xk3OBEbiWsNATZlpP/QN6ewfR9arPt6F5Ws2NU48h8TBJY607RVOC', '123456', 'male', 111222333, 'AndyAdress', 0, 'admin', NULL, '2020-09-02 04:00:49', '2020-09-02 04:00:49'),
(9, 'Anna', 'miracle', 'anna@gmail.com', '$2y$10$kowDSO2IN5OrpnL0vd3xR.WcJtaw9WzauvgbHBIX1nh2FEwXgdWMW', '123456', 'Female', 111222333, 'AndyAdress', 0, 'technical staff', NULL, '2020-09-11 17:00:27', '2020-09-11 17:01:20'),
(10, 'Dmirty', 'dmitryhunter', 'demitry@mail.com', '$2y$10$rhgGHs0jlnVytymgIOizReDfHocclX/8QRH7RbmHHso6bNFFh8r8m', '123456', 'Male', 123456789, 'dmitryAdress', 0, 'technical staff', NULL, '2020-09-11 17:07:05', '2020-09-11 17:07:05'),
(12, 'marry', 'marry', 'marry@mail.com', '$2y$10$rg19G96XQcYvJsymwTbW8.ApwNZhKKT6A2rD/PS8FFBmRbuEIYcka', '123456', 'Male', 123456789, 'marryAdress', 0, 'compliance team', NULL, '2020-09-13 17:39:02', '2020-09-13 18:46:19');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assets`
--
ALTER TABLE `assets`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `assets_barcode_unique` (`barcode`);

--
-- Indexes for table `asset_status_category`
--
ALTER TABLE `asset_status_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `asset_type_category`
--
ALTER TABLE `asset_type_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `oauth_access_tokens`
--
ALTER TABLE `oauth_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD KEY `oauth_access_tokens_user_id_index` (`user_id`);

--
-- Indexes for table `oauth_auth_codes`
--
ALTER TABLE `oauth_auth_codes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `oauth_auth_codes_user_id_index` (`user_id`);

--
-- Indexes for table `oauth_clients`
--
ALTER TABLE `oauth_clients`
  ADD PRIMARY KEY (`id`),
  ADD KEY `oauth_clients_user_id_index` (`user_id`);

--
-- Indexes for table `oauth_personal_access_clients`
--
ALTER TABLE `oauth_personal_access_clients`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `oauth_refresh_tokens`
--
ALTER TABLE `oauth_refresh_tokens`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_username_unique` (`username`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assets`
--
ALTER TABLE `assets`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `asset_status_category`
--
ALTER TABLE `asset_status_category`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `asset_type_category`
--
ALTER TABLE `asset_type_category`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `oauth_clients`
--
ALTER TABLE `oauth_clients`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `oauth_personal_access_clients`
--
ALTER TABLE `oauth_personal_access_clients`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
