<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Auth;

class LogoutController extends Controller
{
    public function __construct()
    {
        $this->middleware('guest', ['except' => ['logout', 'getLogout']]);
    }

    public function action()
    {
        auth('api')->user()->tokens->each(function ($token, $key) {
            $token->delete();
        });

        return response()->json('Logged out', 200);
    }

}
