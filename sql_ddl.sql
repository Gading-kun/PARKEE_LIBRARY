

CREATE DATABASE LibraryDB
GO

USE [LibraryDB]
GO

/****** Object:  Table [dbo].[book_table]    Script Date: 12/1/2024 2:49:03 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[book_table](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[author] [varchar](255) NULL,
	[create_date] [datetime2](6) NULL,
	[isbn] [varchar](255) NULL,
	[publisher] [varchar](255) NULL,
	[stock] [int] NOT NULL,
	[title] [varchar](255) NULL,
	[update_date] [datetime2](6) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE TABLE [dbo].[borrower_table](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[active_status] [varchar](255) NULL,
	[citizen_id] [varchar](255) NULL,
	[create_date] [datetime2](6) NULL,
	[email] [varchar](255) NULL,
	[name] [varchar](255) NULL,
	[update_date] [datetime2](6) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[borrowing_table](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[book_id] [bigint] NULL,
	[borrow_status] [varchar](255) NULL,
	[borrower_id] [bigint] NULL,
	[create_date] [datetime2](6) NULL,
	[date_return_expectation] [datetime2](6) NULL,
	[date_return_realization] [datetime2](6) NULL,
	[isbn_book] [varchar](255) NULL,
	[late_status] [varchar](255) NULL,
	[request_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[borrowing_logs_table](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[action] [varchar](255) NULL,
	[book_id] [bigint] NULL,
	[borrow_status] [varchar](255) NULL,
	[borrower_id] [bigint] NULL,
	[created_date] [datetime2](6) NULL,
	[isbn_book] [varchar](255) NULL,
	[late_status] [varchar](255) NULL,
	[request_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO