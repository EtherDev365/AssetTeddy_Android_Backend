<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use App\Http\Requests\Auth\RegisterRequest;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class RegisterController extends Controller
{
    public function action(Request $request)
    {
        $validator = new RegisterRequest();
        $validator = Validator::make($request->all(), $validator->rules());

        if (!$validator->fails()) {

            $userArray = [
                'name' => $request->get('name'),
                'gender' => $request->get('gender'),
                'contact_number' => $request->get('contact_number'),
                'address' => $request->get('address'),
                'username' => $request->get('username'),
                'email'      => $request->get('email'),
                'role'      => $request->get('role') ,
                'password'   => bcrypt($request->get('password')),
                'confirm_password'   => $request->get('confirm_password')
            ];
            
            $user = User::create($userArray);
            
            if ($user) {
                return response()->json($user);
            } else {
                return response()->json([
                    'status'  => 400,
                    'message' => 'Bad Request',
                ], 400);
            }
        } else {
            return response()->json([
                'status'  => 404,
                'message' => $validator->errors(),
            ], 404);
        }
    }
}
